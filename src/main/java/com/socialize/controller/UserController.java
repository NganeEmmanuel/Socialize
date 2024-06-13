package com.socialize.controller;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.UserNotFoundException;
import com.socialize.model.User;
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

}
