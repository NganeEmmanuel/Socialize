package com.socialize.controller;

import com.socialize.dto.PostDTO;
import com.socialize.service.entityService.FeedServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class FeedController {
    private FeedServiceImpl feedService;

    @GetMapping("/feed")
    public ResponseEntity<List<PostDTO>> getFeed(
            @RequestParam Long id,
            @RequestParam int start,
            @RequestParam int stop) {
        List<PostDTO> feed = feedService.getFeed(id, start, stop);
        return ResponseEntity.ok(feed);
    }

}
