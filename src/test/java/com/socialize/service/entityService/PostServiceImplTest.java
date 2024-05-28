package com.socialize.service.entityService;

import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.model.Post;
import com.socialize.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("all")
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deletePost_existingId_deletesPost() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        assertDoesNotThrow(() -> postService.deletePost(postId));
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void deletePost_nonExistingId_throwsPostNotFoundException() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.deletePost(postId));
        verify(postRepository, never()).delete(any(Post.class));
    }

    @Test
    void getPostById_existingId_returnsPost() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post foundPost = postService.getPostById(postId);
        assertEquals(postId, foundPost.getId());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostById_nonExistingId_throwsPostNotFoundException() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.getPostById(postId));
        verify(postRepository, times(1)).findById(postId);
    }
}

