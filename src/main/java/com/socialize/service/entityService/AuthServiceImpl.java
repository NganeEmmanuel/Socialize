package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.exception.AuthException;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.service.mapperService.UserMapperService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapperService userMapperService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, UserMapperService userMapperService) {
        this.userRepository = userRepository;
        this.userMapperService = userMapperService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void signup(UserDTO userDTO, String password) {
        try {
            // Check if the user already exists
            if (userRepository.findByEmail(userDTO.getEmail()) != null) {
                throw new AuthException("User with email " + userDTO.getEmail() + " already exists");
            }

            // Create a new user entity and save it to the database
            User user = userMapperService.mapToEntity(userDTO);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } catch (Exception e) {
            throw new AuthException("Error occurred during signup", e);
        }
    }

    @Override
    public UserDTO login(String email, String password) {
        try {
            // Find the user by email
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new AuthException("Invalid user credentials");
            }

            // Verify the password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new AuthException("Invalid user credentials");
            }

            // Map the user entity to a DTO and return it
            return userMapperService.mapToDTO(user);
        } catch (Exception e) {
            throw new AuthException("Error occurred during login", e);
        }
    }
}