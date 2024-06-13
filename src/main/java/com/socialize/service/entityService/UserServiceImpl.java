package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.enums.UserStatus;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.exception.exceptions.UserNotFoundException;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.service.mapperService.UserMapperService;
import com.socialize.utils.UpdateUtils;
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

    private final UserRepository userRepository;
    private final UserMapperService userMapper;
    private  final PasswordEncoder passwordEncoder;


    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userDTO.getId()));

            UpdateUtils.copyNonNullProperties(userDTO, user);
            user.setLastUpdated(new Date());
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
    public UserDTO getUserByUsername(String username) {
        try{
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NoMatchingUserFoundException(username));
            return userMapper.mapToDTO(user);
        }catch (NoMatchingUserFoundException ex){
            logger.info("Could not find user with username: {}", username);
            throw new RuntimeException(ex);
        }catch (Exception e){
            logger.info("An error occurred while getting the user with username: {}", username);
            throw new RuntimeException(e);
        }
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

    @Override
    public UserDTO deactivateUser(Long userId) {
        User user;
        try {
            user = userRepository.findById(userId)
                    .orElseThrow(()-> new UserNotFoundException(userId) );
            user.setUserStatus(UserStatus.SUSPENDED);
            User updatedUser = userRepository.save(user);
//            UpdateUtils.copyNonNullProperties(user, updatedUser);
            updatedUser.setLastUpdated(new Date());
            return userMapper.mapToDTO(updatedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void followUser(Long userId, Long followId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User followUser = userRepository.findById(followId).orElseThrow(() -> new UserNotFoundException(followId));
        // adding user to the followers of followId
        followUser.getFollowers().add(user);
        userRepository.save(followUser);
    }

    @Override
    public void unfollowUser(Long userId, Long followId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User followUser = userRepository.findById(followId).orElseThrow(() -> new RuntimeException("user to unfollow not found"));
        //un follow user
        user.getFollowing().remove(followUser);
        userRepository.save(user);
        followUser.getFollowers().remove(user);
        userRepository.save(followUser);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            throw new RuntimeException(" password does not match, check again!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
