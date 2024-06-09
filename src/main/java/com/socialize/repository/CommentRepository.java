package com.socialize.repository;

import com.socialize.exception.exceptions.CommentNotFoundException;
import com.socialize.exception.exceptions.NoChildCommentFoundException;
import com.socialize.model.Comment;
import com.socialize.model.Post;
import com.socialize.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    Optional<List<Comment>> findByPost(Post post, Pageable pageable) throws CommentNotFoundException;

    Optional<List<Comment>> findCommentsByPostAndParentCommentId(Post post,Integer pId, Pageable pageable) throws NoChildCommentFoundException;
}
