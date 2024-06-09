package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDTO getUserById(Long userId);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);

    List<UserDTO> getAllUsers();



}
