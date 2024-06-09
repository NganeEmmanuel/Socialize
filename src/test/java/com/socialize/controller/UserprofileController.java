package com.socialize.controller;

import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user/profile")
//todo Carole add RequireArgsConstructor annotation
public class UserprofileController {
    @Autowired // todo remove this after adding the annotation i specified above
    //todo Carole remove commented code
    /*private UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUserProfile(@RequestParam("userId") Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }*/
    private final UserService userService;

    //todo Carole this constructor is not needed. the annotaion i specified above automatically creates this for you. so remove
    public UserprofileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestParam("userId") Long userId) {
        UserDTO userDTO = userService.getUserProfile(userId);
        return ResponseEntity.ok(userDTO);
    }
}


//Todo Carole please remove everything from here below they are useless
// UserService.java
public interface UserService {
    UserDTO getUserProfile(Long userId);
}

// UserServiceImpl.java
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return userMapper.toDTO(user);
    }
}

// UserDTO.java
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    // Add other user-related fields
}

// UserMapper.java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}


