package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.model.User;
import com.socialize.repository.UserRepository;
import com.socialize.service.mapperService.UserMapperService;
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
    private final UserMapperService userMapperService;
    private final UserRepository userRepository ;

    /**
     *  Retrieve a UserDTO matching the provided username
     *
     * @param username the username to search for
     * @param start the start index of the search
     * @param stop the stop index of the search
     * @return a list of UserDTOs matching the provided username
     * @throws NoMatchingUserFoundException if no user is found with a name or username like what's provided
     */
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

    /**
     *
     * @param user the user we want to convert to a UserDTO
     * @return UserDTO object
     */
    private UserDTO convertToDto(User user) {
        return userMapperService.mapToDTO(user);
    }

}
