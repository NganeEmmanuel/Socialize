package com.socialize.service.entityService;

import com.socialize.auth.AuthenticationRefreshResponse;
import com.socialize.auth.AuthenticationRequest;
import com.socialize.auth.AuthenticationResponse;
import com.socialize.auth.RegisterRequest;
import com.socialize.enums.UserAuthority;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.exception.exceptions.TokenExpiredException;
import com.socialize.exception.exceptions.UserNotFoundException;
import com.socialize.model.TokenBlacklist;
import com.socialize.model.User;
import com.socialize.repository.TokenBlacklistRepository;
import com.socialize.repository.UserRepository;
import com.socialize.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public AuthenticationResponse signup(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authority(UserAuthority.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(); //todo throw and handle an appropriate exception
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public String logout(String token) {
        var tokenBlacklist = TokenBlacklist.builder()
                .token(token)
                .expirationDate(jwtService.extractExpiration(token))
                .build();
        tokenBlacklistRepository.save(tokenBlacklist);
        return "User logged out successfully.";
    }

    @Override
    public AuthenticationRefreshResponse refreshToken(String refreshToken) {
        try {
            if (jwtService.isJwtTokenExpired(refreshToken)) {
                throw new TokenExpiredException();
            }
            var username = jwtService.extractUserName(refreshToken);
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NoMatchingUserFoundException(username));
            var newJwtToken = jwtService.generateJwtToken(user);
            return AuthenticationRefreshResponse.builder()
                    .token(newJwtToken)
                    .build();
        }catch (TokenExpiredException expired){
            logger.info("Expired token while refreshing token: {}", refreshToken);
            throw new RuntimeException(expired);
        }catch (NoMatchingUserFoundException ex){
            logger.info("Error getting user from refresh token: {}", refreshToken);
            throw new RuntimeException(ex);
        }
    }
}