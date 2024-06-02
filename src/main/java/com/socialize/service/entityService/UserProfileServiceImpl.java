package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.service.mapperService.UserMapperService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapperService userMapperService;

    public UserProfileServiceImpl(UserRepository userRepository, UserMapperService userMapperService) {
        this.userRepository = userRepository;
        this.userMapperService = userMapperService;
    }

    @Override
    public UserDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoMatchingUserFoundException("User not found with ID: " + userId));
        return userMapperService.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapperService::toDTO)
                .collect(Collectors.toList());
    }
}