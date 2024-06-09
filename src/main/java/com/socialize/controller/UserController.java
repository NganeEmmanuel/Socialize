package com.socialize.controller;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.UserNotFoundException;
import com.socialize.model.User;
import com.socialize.service.entityService.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

}
