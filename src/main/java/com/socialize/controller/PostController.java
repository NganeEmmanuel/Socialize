package com.socialize.controller;

import com.socialize.dto.PostDTO;
import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.service.entityService.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/post")
@RequiredArgsConstructor
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    /**
     * Deletes a post by its ID.
     *
     * @param id The ID of the post to delete.
     * @return A ResponseEntity containing a success message and HTTP status.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try {
            logger.info("API call to delete post with ID: {}", id);
            postService.deletePost(id);
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        } catch (PostNotFoundException ex) {
            logger.error("Error deleting post with ID: {}", id, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get a post by its ID.
     *
     * @param id The ID of the post to get.
     * @return A ResponseEntity containing PostDTO and HTTP status.
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        try {
            PostDTO postDTO = postService.getPostDTOById(id);
            return ResponseEntity.ok(postDTO);
        } catch (PostNotFoundException ex) {
            logger.error("Post not found with ID: {}", id, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while retrieving post with ID: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
