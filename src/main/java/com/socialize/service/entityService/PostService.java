package com.socialize.service.entityService;

import com.socialize.dto.PostDTO;
import com.socialize.enums.ReactionType;
import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.model.Post;

public interface PostService {
    void deletePost(Long id) throws PostNotFoundException;

    Post getPostById(Long postId);

    PostDTO getPostDTOById(Long postId) throws PostNotFoundException;

    void updatePost(Long postId, PostDTO postDTO) throws PostNotFoundException;

    PostDTO createPost(PostDTO postDTO);

    void reactToPost(Long userId, Long postId, ReactionType reactionType) throws Exception;
}

