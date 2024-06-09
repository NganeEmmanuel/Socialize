package com.socialize.controller;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.ProfileNotFoundException;
import com.socialize.service.entityService.UserProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserProfileController {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    private final UserProfileServiceImpl userProfileServiceImpl;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestParam("userId") Long userId) {
        try {
            UserDTO userDTO = userProfileServiceImpl.getUserProfileById(userId);
            return ResponseEntity.ok(userDTO);
        } catch (ProfileNotFoundException ex) {
            logger.error("User not found with ID: {}", userId, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while retrieving user with ID: {}", userId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}