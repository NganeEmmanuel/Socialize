package com.socialize.service.entityService;

import com.socialize.auth.AuthenticationRefreshResponse;
import com.socialize.auth.AuthenticationRequest;
import com.socialize.auth.AuthenticationResponse;
import com.socialize.auth.RegisterRequest;
import com.socialize.enums.UserAuthority;
import com.socialize.enums.UserStatus;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.exception.exceptions.TokenExpiredException;
import com.socialize.exception.exceptions.UserNotVerifiedException;
import com.socialize.model.TokenBlacklist;
import com.socialize.model.User;
import com.socialize.repository.TokenBlacklistRepository;
import com.socialize.repository.UserRepository;
import com.socialize.security.jwt.JwtService;
import com.socialize.service.mailService.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistRepository tokenBlacklistRepository;private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final EmailService emailService;

    private Map<String, String> verificationTokens = new HashMap<>(); // Temporary storage for tokens

    @Override
    public AuthenticationResponse signup(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authority(UserAuthority.USER)
                .userStatus(UserStatus.PENDING)
                .build();
        userRepository.save(user);
        //todo configure mail connectioin and uncomment
        //send confirmation mail
        //String token = generateVerificationToken(user.getEmail());
        //sendVerificationEmail(user.getEmail(), token);

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

        try {
            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new NoMatchingUserFoundException(request.getUsername()));
            //todo configure mail connectioin and uncomment
            // if (user.getUserStatus().equals(UserStatus.PENDING)) {
            //     throw new UserNotVerifiedException(user.getId());
            // }
            var jwtToken = jwtService.generateJwtToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }catch (NoMatchingUserFoundException ex){
            logger.info("Error getting user while logging in with username: {}", request.getUsername());
            throw new RuntimeException(ex);
        }catch (UserNotVerifiedException ex){
            logger.info("Email not verified for user: {}", request.getUsername());
            throw new RuntimeException(ex);
        }catch (Exception e){
            logger.info("Error occurred while logging in user: {}", request.getUsername());
            throw new RuntimeException(e);
        }
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

    private String generateVerificationToken(String email) {
        String token = BCrypt.gensalt();
        verificationTokens.put(token, email);
        return token;
    }

    private void sendVerificationEmail(String email, String token) {
        String verificationUrl = "https://socialize-production.up.railway.app/api/auth/verify?token=" + token;
        String subject = "Socialize: Verify Your Email";
        String body = "Welcome to Socialize! Please verify your email by clicking the link below:\n\n" +
                "<a href=\"" + verificationUrl + "\">Verify Email</a>\n\n" +
                "Thank you for joining us!";
        emailService.sendEmail(email, subject, body);
    }

    public boolean verifyEmail(String token) {
        String email = verificationTokens.get(token);
        if (email != null) {
            try {
                User user = userRepository.findByEmail(email).orElseThrow(() -> new NoMatchingUserFoundException(email));
                user.setUserStatus(UserStatus.ACTIVE);
                userRepository.save(user);
                verificationTokens.remove(token);
                return true;
            }catch (NoMatchingUserFoundException ex){
                logger.info("Error getting user while verifying email: {}", email);
                throw new RuntimeException(ex);
            }catch (Exception e){
                logger.info("An Error occurred while verifying email: {}", email);
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
