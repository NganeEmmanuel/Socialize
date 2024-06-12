package com.socialize.controller;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.exception.exceptions.UserNotFoundException;
import com.socialize.service.entityService.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * ret
     * @param userDTO
     * @return
     */
    @PutMapping("/edit/profile")
    public ResponseEntity<UserDTO> editProfile(@RequestBody UserDTO userDTO){
        try {
            logger.info("API call to edit the profile of a user with ID: {}", userDTO.getId());

            UserDTO updatedUser = userService.updateUser(userDTO.getId(), userDTO);
        return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException ex) {
            logger.error("Error fetching the user with ID: {}", userDTO.getId(), ex);
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.error("An error occurred while updating the user with ID: {}", userDTO.getId(), e);
            return ResponseEntity.status(500).body(null);
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
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            logger.error("Error deleting user with ID: {}", userId, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred while deactivating the user with ID: {}", userId, e);
            return ResponseEntity.status(500).body(null);
        }
    }

}
