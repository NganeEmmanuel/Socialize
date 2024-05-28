package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private final UserRepository userRepository ;
    @Override
    public List<UserDTO> getUserDTOByUsername(String username,int start,int stop) throws NoMatchingUserFoundException {
        try {
            // Escape any special characters in the username to avoid injection issues
            String escapedUsername = username.replace("%", "\\%"); // Escape the % wildcard character

            String username_without_quotes = escapedUsername.substring(1, escapedUsername.length() - 1);

            Pageable pageable = PageRequest.of(start, stop - start);
            List<User> users = userRepository.findByUsernameContainingOrNameContaining(username_without_quotes,pageable);

            // Convert User entities to UserDTOs
            return users.stream().map(this::convertToDto).collect(Collectors.toList());
        }catch (NoMatchingUserFoundException ex){
            logger.error("User not found with username/name like : {}", username, ex);
            throw ex;
        }catch (Exception ex) {
            logger.error("An unexpected error occurred while looking for a user matching your input : {}", username, ex);
            throw new RuntimeException("An unexpected error occurred", ex);
        }

    }
    private UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setAuthority(user.getAuthority());
        userDTO.setMediaMimeType(user.getMediaMimeType());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

}
