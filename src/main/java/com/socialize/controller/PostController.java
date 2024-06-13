package com.socialize.controller;

import com.socialize.dto.PostDTO;
import com.socialize.enums.ReactionType;
import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.service.entityService.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@CrossOrigin
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

    /**
     * Updates a post by its ID.
     *
     * @param id      The ID of the post to update.
     * @param postDTO The updated post data.
     * @return A ResponseEntity containing a success message and HTTP status.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        try {
            logger.info("API call to update post with ID: {}", id);
            postService.updatePost(id, postDTO);
            return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
        } catch (PostNotFoundException ex) {
            logger.error("Error updating post with ID: {}", id, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while updating post with ID: {}", id, ex);
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/new")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        try{
           PostDTO createdPost = postService.createPost(postDTO);
           return new ResponseEntity<>(createdPost, HttpStatus.OK);
        }catch(Exception ex){
            logger.error("error creating new post", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/react")
    public ResponseEntity<String> reactToPost(@RequestParam Long userId, @RequestParam Long postId) {
        try {
            postService.reactToPost(userId, postId, ReactionType.HEART);
            return ResponseEntity.ok("Reaction added to post successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/react/undo")
    public ResponseEntity<String> undoReactPost(@RequestParam Long userId, @RequestParam Long postId) {
        try {
            postService.undoReactPost(userId, postId);
            return ResponseEntity.ok("Reaction removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
