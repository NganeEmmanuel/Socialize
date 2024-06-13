package com.socialize.service.entityService;

import com.socialize.exception.exceptions.ProfileNotFoundException;
import com.socialize.repository.UserRepository;
import com.socialize.dto.UserDTO;
import com.socialize.model.User;
import com.socialize.service.mapperService.UserMapperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);
    private final UserMapperService userMapperService;
    private final UserRepository userRepository ;


    public UserDTO getUserProfileById(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ProfileNotFoundException("User not found with ID: " + userId));
            return userMapperService.mapToDTO(user);
        } catch (ProfileNotFoundException e) {
            logger.error("User not found with ID: {}", userId, e);
            throw e;

        }
    }

}
