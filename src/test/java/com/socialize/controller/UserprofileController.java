package com.socialize.controller;

import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user/profile")
//todo Carole add RequireArgsConstructor annotation
public class UserprofileController {
    @Autowired // todo remove this after adding the annotation i specified above
    //todo Carole remove commented code


    //todo Carole this constructor is not needed. the annotaion i specified above automatically creates this for you. so remove

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestParam("userId") Long userId) {
        UserDTO userDTO = userService.getUserProfile(userId);
        return ResponseEntity.ok(userDTO);
    }
}






