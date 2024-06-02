package com.socialize.controller;


import com.socialize.dto.CommentDTO;
import com.socialize.exception.exceptions.CommentNotFoundException;
import com.socialize.service.entityService.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentServiceImpl commentService;

    /**
     *
     * @param postId the post the comment belongs to
     * @param start where to start
     * @param stop where to stop
     * @return ResponseEntity<List<CommentDTO>> containing the dto comment objects
     */
    @GetMapping("/parent")
    public ResponseEntity<List<CommentDTO>> getComment(
            @RequestParam Long postId,
            @RequestParam int start,
            @RequestParam int stop) {

        try {
            logger.info("API call to fetch comments for post with ID: {}", postId);
            List<CommentDTO> commentList = commentService.getComment(postId, start, stop);
            return ResponseEntity.ok(commentList);
        } catch (CommentNotFoundException ex) {
            logger.error("Error fetching the comments for post with ID: {}", postId, ex);
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception ex){
            logger.error("An unexpected error occurred while looking for comments with the post having the id: {}", postId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/child")
    public ResponseEntity<List<CommentDTO>> getChildComments(
            @RequestParam Long postId,
            @RequestParam Integer parentCommentId,
            @RequestParam int start,
            @RequestParam int stop) {

        try {
            logger.info("API call to fetch comments for comment with ID: {} and post with id: {}", parentCommentId,postId);
            List<CommentDTO> commentList = commentService.getChildComment(postId,parentCommentId, start, stop);
            return ResponseEntity.ok(commentList);
        } catch (CommentNotFoundException ex) {
            logger.error("Error fetching the comments for post with ID: {} and for comment with ID: {} ", postId,parentCommentId, ex);
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception ex){
            logger.error("An unexpected error occurred while looking for parent comments with the post having the id: {} and his parent having for id: {}", postId,parentCommentId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
