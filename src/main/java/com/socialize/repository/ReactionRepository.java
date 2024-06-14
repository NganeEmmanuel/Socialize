package com.socialize.repository;

import com.socialize.model.Post;
import com.socialize.model.Reaction;
import com.socialize.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction>findByUserAndPost(User user, Post post);
}
