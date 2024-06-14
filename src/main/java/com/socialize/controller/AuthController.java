package com.socialize.controller;

import com.socialize.auth.AuthenticationRefreshResponse;
import com.socialize.auth.AuthenticationRequest;
import com.socialize.auth.AuthenticationResponse;
import com.socialize.auth.RegisterRequest;
import com.socialize.dto.UserDTO;
import com.socialize.service.entityService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            return ResponseEntity.ok(authService.logout(token));
        } else {
            return ResponseEntity.badRequest().body("Invalid Authorization header.");
        }
    }

    @GetMapping("/get-looged-user")
    public ResponseEntity<UserDTO> getLoggedInUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            return ResponseEntity.ok(authService.getLoggedInUser(token));
        } else {
            return ResponseEntity.badRequest().body(new UserDTO());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationRefreshResponse> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        boolean isVerified = authService.verifyEmail(token);
        if (isVerified) {
            return ResponseEntity.ok("Email verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification token.");
        }
    }
}
