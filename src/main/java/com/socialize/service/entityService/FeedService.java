package com.socialize.service.entityService;


import com.socialize.dto.PostDTO;
import com.socialize.model.Post;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FeedService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    /**
     * Fetches posts from users in the following list
     * @param id
     * @param start
     * @param stop
     * @return a list of posts DTOs
     */
    public List<PostDTO> getFeed(Long id, int start, int stop) {
        Set<User> following = userRepository.findFollowingById(id);

        List<User> followingList;
        if (following.isEmpty()) {
            // If the following set is empty, use the default users
            followingList = getDefaultUsers();
        } else {
            // Otherwise, use the followed users
            followingList = new ArrayList<>(following);
        }
        // Fetch posts with pagination
        Pageable pageable = PageRequest.of(start, stop - start);
        List<Post> posts = postRepository.findByUserIn(followingList, pageable);

        // Shuffle posts
        Collections.shuffle(posts);

        // Convert to DTOs
        return posts.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Fetches the top 10 users
     * @return a list of users
     */
    private List<User> getDefaultUsers() {
        return userRepository.findAll(PageRequest.of(0, 10)).getContent(); // Example: top 10 users
    }

    private PostDTO convertToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setUserId(post.getUser().getId());
        postDTO.setContent(post.getContent());
        return postDTO;
    }
}
