package com.socialize.service.entityService;

import com.socialize.dto.CommentDTO;
import com.socialize.repository.CommentRepository;

import java.util.List;


public interface CommentService {

    public List<CommentDTO> getComment(Long postId, int start, int stop) ;

}
