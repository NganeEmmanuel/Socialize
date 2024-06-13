package com.socialize.controller;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.exception.exceptions.UserNotFoundException;
import com.socialize.service.entityService.PostService;
import com.socialize.service.entityService.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);



    private final UserService userService;
    private final PostService postService;

    @GetMapping("/following")
    public ResponseEntity<List<UserDTO>> getFollowingUsers(
            @RequestParam Long userId,
            @RequestParam int start,
            @RequestParam int stop
    ){
        List<UserDTO> followingUsers = userService.getFollowingUsers(userId, start, stop);
        return  ResponseEntity.ok(followingUsers);
    }

    /**
     * @param userDTO user object received from the request
     * @return user object on success and a 404 status on failure
     */
    @PutMapping("/edit/profile")
    public ResponseEntity<UserDTO> editProfile(@RequestBody UserDTO userDTO){
        try {
            UserDTO updatedUser = userService.updateUser(userDTO.getId(), userDTO);
        return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException ex) {
            logger.error("Error fetching the user with ID: {}", userDTO.getId(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("An error occurred while updating the user with ID: {}", userDTO.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestParam("userId") Long userId, @RequestParam("followId")Long followId){
        try{
            userService.followUser(userId, followId);
            return ResponseEntity.ok("user  followed  successfully");
        }catch(UserNotFoundException ex){
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("an error occurred");
        }


    }

    /**
     * @param username username of user to get
     * @return user object on success and a 404 status on failure
     */
    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam Long userId, @RequestParam Long followId){
        try{
            userService.unfollowUser(userId, followId);
            return ResponseEntity.ok("user unfollowed successfully");
        }catch(Exception e){
            return ResponseEntity.status(500).body("an error occurred" + e.getMessage());
        }
    }

    @PostMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestParam Long userId, @RequestParam String oldPassword, @RequestParam String newPassword){
        try{
            userService.updatePassword(userId,oldPassword,newPassword);
            return ResponseEntity.ok("Password updated successfully");
        }catch(Exception e){
            return ResponseEntity.status(500).body("an error occurred" + e.getMessage());
        }
    }

    @PutMapping("/deactivate")
    public ResponseEntity<UserDTO> deactivateUser(@RequestParam Long userId){
        try {
            logger.info("API call to deactivate the user with ID: {}", userId);
            UserDTO updatedUser = userService.deactivateUser(userId);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException ex) {
            logger.error("Error fetching the user with ID: {}", userId, ex);
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.error("An error occurred while deactivating the user with ID: {}", userId, e);
            return ResponseEntity.status(500).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long userId){
        try {
            logger.info("API call to delete the user with ID: {}", userId);
            userService.deleteUser(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            logger.error("Error deleting user with ID: {}", userId, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the user with ID: {}", userId, e);
            return ResponseEntity.status(500).body(null);
        }
    }

}
