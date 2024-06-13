package com.socialize.service.entityService;

import com.socialize.auth.AuthenticationRefreshResponse;
import com.socialize.auth.AuthenticationRequest;
import com.socialize.auth.AuthenticationResponse;
import com.socialize.auth.RegisterRequest;
import com.socialize.exception.exceptions.AuthException;

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

    /**
     * Authenticates a user and returns the user's data transfer object.
     *
     * @param token    token to be blacklisted
     * @return a success status and message is successful
     */
    String logout(String token);

    /**
     * Registers a new user in the system.
     *
     * @param refreshToken the token to be refreshed
     * @return a refreshed authentication jwt token on success
     * @throws com.socialize.exception.exceptions.TokenExpiredException if token has expired
     */
    AuthenticationRefreshResponse refreshToken(String refreshToken);

    boolean verifyEmail(String token);
}
