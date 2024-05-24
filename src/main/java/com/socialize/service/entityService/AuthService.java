package com.socialize.service.entityService;

import com.socialize.auth.AuthenticationRequest;
import com.socialize.auth.AuthenticationResponse;
import com.socialize.auth.RegisterRequest;
import com.socialize.dto.UserDTO;
import com.socialize.exception.AuthException;

public interface AuthService {
    /**
     * Registers a new user in the system.
     *
     * @param request the user data transfer object containing the user's information
     * @throws AuthException if the user with the given email already exists or an error occurs during signup
     */
    AuthenticationResponse signup(RegisterRequest request);

    /**
     * Authenticates a user and returns the user's data transfer object.
     *
     * @param request    contains the email and password of the user
     * @return the user data transfer object if the login is successful
     * @throws AuthException if the user with the given email is not found or the password is invalid
     */
    AuthenticationResponse login(AuthenticationRequest request);
}
