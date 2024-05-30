package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.service.mapperService.UserMapperService;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl extends UserProfileService {
    private final UserRepository userRepository;
    private final UserMapperService userMapper;

    public UserProfileServiceImpl(UserRepository userRepository, UserMapperService userMapper, UserMapperService userMapper1) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

    }

    @Override
    public UserDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoMatchingUserFoundException("User not found with ID: " + userId));
        return userMapper.toDTO(user);
    }
}