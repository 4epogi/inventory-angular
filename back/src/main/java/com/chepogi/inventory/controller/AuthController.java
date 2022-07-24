package com.chepogi.inventory.controller;

import com.chepogi.inventory.dto.AuthenticationResponse;
import com.chepogi.inventory.dto.RefreshTokenRequest;
import com.chepogi.inventory.dto.RegisterRequest;
import com.chepogi.inventory.dto.LoginRequest;
import com.chepogi.inventory.service.AuthService;
import com.chepogi.inventory.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(authService.login(loginRequest));
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid login or password", ex);
        }
    }

    @PostMapping("/refresh/token")
    public  AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh token was successfully deleted");
    }

}
