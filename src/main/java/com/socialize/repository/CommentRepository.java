package com.socialize.repository;

import com.socialize.model.Comment;
import com.socialize.model.Post;
import com.socialize.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByPost(Post post, Pageable pageable);

}
