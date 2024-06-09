package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.service.entityService.UserService;
import com.socialize.service.mapperService.MapperService;
import com.socialize.service.mapperService.UserMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapperService userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapperService userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

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
        User savedUser = userRepository.save(user);
        return userMapper.mapToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
//        User existingUser = null;
//        try {
//            existingUser = userRepository.findById(userId)
//                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
//        } catch (ChangeSetPersister.NotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        userMapper.updateEntityFromDTO(userDTO, existingUser);
//        User updatedUser = userRepository.save(existingUser);
//        return userMapper.mapToDTO(updatedUser);
        return null;
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
    public UserDTO getUserDTOById(Long userId) throws Exception {
        User user;
        try {
             user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
           
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while retrieving user with ID: {}", userId, ex);
            throw ex;
        }

        return userMapper.mapToDTO(user);
}
}
