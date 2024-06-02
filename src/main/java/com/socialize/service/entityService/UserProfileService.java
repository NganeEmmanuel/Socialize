package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.service.mapperService.UserMapperService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    private final UserRepository userRepository;
    private final UserMapperService userMapperService;

    public UserProfileService(UserRepository userRepository, UserMapperService userMapperService) {
        this.userRepository = userRepository;
        this.userMapperService = userMapperService;
    }

    public UserDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoMatchingUserFoundException("User not found with ID: " + userId));
        return userMapperService.toDTO(user);
    }
}