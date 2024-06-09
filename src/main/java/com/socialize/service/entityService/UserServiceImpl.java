package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.exception.exceptions.UserNotFoundException;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.service.mapperService.UserMapperService;
import com.socialize.utils.UpdateUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapperService userMapper;


    @Override
    public UserDTO getUserById(Long userId) {
        User user;
        try {
            user = userRepository.findById(userId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        return userMapper.mapToDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.mapToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.mapToDTO(savedUser);
    }


    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userDTO.getId()));
            UpdateUtils.copyNonNullProperties(userDTO, user);
            user.setLastUpdated(new Date());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User updatedUser = userRepository.save(user);
            return userMapper.mapToDTO(updatedUser);
        } catch (Exception e) {
            logger.info("An error occurred while updating the user with ID: {}", userId);
            throw new RuntimeException(e);
        }


    }
    @Override
    public void deleteUser(Long userId) {
        User user;
        try {
            user = userRepository.findById(userId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return userMapper.mapToDTOList(users);
        return null;
    }
    @Override
    @Transactional
    public List<UserDTO> getFollowingUsers(Long userId, int start, int stop) {
        try {
            // Try to find the user and their following users
            List<User> followingUsers = userRepository.findFollowingByUserId(userId);

            // Adjust start and stop indices
            start = Math.max(0, start);
            stop = Math.min(followingUsers.size(), stop);
            start = Math.min(start, stop);

            // Paginate and map to DTOs
            List<UserDTO> result = followingUsers.subList(start, stop).stream()
                    .map(userMapper::mapToDTO)
                    .collect(Collectors.toList());

            // Print the result to the console
            for (UserDTO userDTO : result) {
                System.out.println(userDTO.toString());
            }

            return result;

        } catch (UserNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }


}
