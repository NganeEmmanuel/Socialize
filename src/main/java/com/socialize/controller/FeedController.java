package com.socialize.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class FeedController {
    @GetMapping("/feed")
    public ResponseEntity<?> getUserFeed(){
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(){
        return null;
    }
}
