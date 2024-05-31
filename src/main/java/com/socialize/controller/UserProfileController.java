package com.socialize.controller;

import com.socialize.dto.UserDTO;
import com.socialize.service.entityService.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @GetMapping
    public ResponseEntity<UserDTO> getUserProfile(@RequestParam("userId") Long userId) {
        UserDTO userDTO = userProfileService.getUserProfile(userId);
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new UserDTO("User not found"));
        }
        return ResponseEntity.ok(userDTO);
    }
}