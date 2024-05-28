package com.socialize.service.mapperService;

import com.socialize.dto.PostDTO;
import com.socialize.model.Post;
import org.springframework.stereotype.Service;

@Service
public class PostMapperService implements MapperService<Post, PostDTO> {

    @Override
    public Post mapToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setUser(new UserMapperService().mapToEntity(postDTO.getUser()));
        post.setContent(postDTO.getContent());
        post.setMediaFileContent(postDTO.getMediaContent());
        post.setMediaFileName(postDTO.getMediaName());
        post.setMediaMimeType(postDTO.getMediaType());
        post.setTotalReactions(postDTO.getTotalReactions());
        post.setTotalComments(postDTO.getTotalComments());
        // Set additional fields as needed
        return post;
    }

    @Override
    public PostDTO mapToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setUser(new UserMapperService().mapToDTO(post.getUser()));
        postDTO.setContent(post.getContent());
        postDTO.setMediaContent(post.getMediaFileContent());
        postDTO.setMediaType(post.getMediaMimeType());
        postDTO.setMediaName(post.getMediaFileName());
        postDTO.setTotalReactions(post.getTotalReactions());
        postDTO.setTotalComments(post.getTotalComments());
        // Set additional fields as needed
        return postDTO;
    }
}
