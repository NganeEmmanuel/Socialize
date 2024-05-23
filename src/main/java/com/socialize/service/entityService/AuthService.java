package com.socialize.service.entityService;

import com.socialize.dto.UserDTO;
import com.socialize.exception.AuthException;

public interface AuthService {
    /**
     * Registers a new user in the system.
     *
     * @param userDTO the user data transfer object containing the user's information
     * @throws AuthException if the user with the given email already exists or an error occurs during signup
     */
    void signup(UserDTO userDTO, String password);

    /**
     * Authenticates a user and returns the user's data transfer object.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return the user data transfer object if the login is successful
     * @throws AuthException if the user with the given email is not found or the password is invalid
     */
    UserDTO login(String email, String password);
}
