package com.socialize.service.entityService;

import com.socialize.dto.CommentDTO;
import com.socialize.repository.CommentRepository;

import java.util.List;


public interface CommentService {

    List<CommentDTO> getComment(Long postId, int start, int stop) ;
    List<CommentDTO> getChildComment(Long postId, Integer parentCommentId, int start, int stop) ;

}
