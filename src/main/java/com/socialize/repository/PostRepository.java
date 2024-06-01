package com.socialize.repository;

import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.model.Post;
import com.socialize.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Fetches posts from users in the following list
     * @param followingList list of users to fetch posts from
     * @param pageable pagination information
     * @return list of posts
     * @author Nde Dilan
     */
    List<Post> findByUserIn(List<User> followingList, Pageable pageable);

    Post findPostById(Long postId) throws PostNotFoundException;

}
