package com.socialize.service.mapperService;

import com.socialize.dto.UserDTO;
import com.socialize.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapperService implements MapperService<User, UserDTO> {

    @Override
    public User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUserName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setMediaMimeType(userDTO.getMediaMimeType());
        // Set additional fields as needed
        return user;
    }

    @Override
    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setMediaMimeType(user.getMediaMimeType());
        // Set additional fields as needed
        return userDTO;
    }
}
