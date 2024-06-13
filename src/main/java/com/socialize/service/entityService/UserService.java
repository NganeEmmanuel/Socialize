package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);

    UserDTO getUserByUsername(String username);

    List<UserDTO> getFollowingUsers(Long userId, int start, int stop);
}
