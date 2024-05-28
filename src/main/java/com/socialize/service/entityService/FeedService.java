package com.socialize.service.entityService;

import com.socialize.dto.PostDTO;
import com.socialize.model.Post;
import com.socialize.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface FeedService {
    public List<PostDTO> getFeed(Long id, int start, int stop);

     List<User> getDefaultUsers();

     PostDTO convertToDto(Post post) ;
}
