package com.socialize.service.entityService;
import com.socialize.controller.UserProfileController;
import com.socialize.dto.UserDTO;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public abstract class UserProfileService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    private UserDTO convertToDTO(User user) {
        // Convert the User entity to a UserDTO
        // You can use a library like ModelMapper or write the conversion manually
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setAuthority(user.getAuthority());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setMediaMimeType(user.getMediaMimeType());
        return userDTO;
    }

    public abstract UserDTO getUserProfile(Long userId);
}
