package com.socialize.controller;

import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.service.entityService.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deletePost_existingId_returnsSuccessMessage() {
        Long postId = 1L;

        doNothing().when(postService).deletePost(postId);

        ResponseEntity<String> response = postController.deletePost(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post deleted successfully", response.getBody());
        verify(postService, times(1)).deletePost(postId);
    }

    @Test
    void deletePost_nonExistingId_returnsNotFoundStatus() {
        Long postId = 1L;

        doThrow(new PostNotFoundException(postId)).when(postService).deletePost(postId);

        ResponseEntity<String> response = postController.deletePost(postId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Post not found with ID: " + postId, response.getBody());
        verify(postService, times(1)).deletePost(postId);
    }


}

