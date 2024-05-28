package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;

import java.util.List;

public interface SearchService {

    List<UserDTO> getUserDTOByUsername(String username);
}
