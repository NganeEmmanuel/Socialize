package com.socialize.controller;

import com.socialize.dto.UserDTO;
import com.socialize.service.entityService.UserProfileServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")

public class UserProfileController {
    private final UserProfileServiceImpl userProfileService;

    public UserProfileController(UserProfileServiceImpl userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestParam("userId") Long userId) {
        UserDTO userDTO = userProfileService.getUserProfile(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}