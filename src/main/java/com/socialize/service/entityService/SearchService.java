package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.exception.exceptions.PostNotFoundException;

import java.util.List;

public interface SearchService {
    List<UserDTO> getUserDTOByUsername(String username,int start,int stop) throws NoMatchingUserFoundException;
}
