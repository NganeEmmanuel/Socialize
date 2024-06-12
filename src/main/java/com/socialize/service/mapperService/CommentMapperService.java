package com.socialize.service.mapperService;

import com.socialize.dto.CommentDTO;
import com.socialize.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentMapperService implements MapperService<Comment, CommentDTO> {

    @Override
    public Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        comment.setParentCommentId(commentDTO.getParentCommentId());
        comment.setTotalReactions(commentDTO.getTotalReaction());
        comment.setUser(new UserMapperService().mapToEntity(commentDTO.getUser()));
        comment.setPost(new PostMapperService().mapToEntity(commentDTO.getPost()));
        // Set additional fields as needed
        return comment;
    }

    @Override
    public CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setParentCommentId(comment.getParentCommentId());
        commentDTO.setTotalReaction(comment.getTotalReactions());
        commentDTO.setUser(new UserMapperService().mapToDTO(comment.getUser()));
        commentDTO.setPost(new PostMapperService().mapToDTO(comment.getPost()));
        // Set additional fields as needed
        return commentDTO;
    }
}
