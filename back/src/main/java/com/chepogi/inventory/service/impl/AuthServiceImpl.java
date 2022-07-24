package com.chepogi.inventory.service.impl;

import com.chepogi.inventory.dto.AuthenticationResponse;
import com.chepogi.inventory.dto.LoginRequest;
import com.chepogi.inventory.dto.RefreshTokenRequest;
import com.chepogi.inventory.dto.RegisterRequest;
import com.chepogi.inventory.exceptions.AppRoleException;
import com.chepogi.inventory.repository.AppRoleRepository;
import com.chepogi.inventory.repository.AppUserRepository;
import com.chepogi.inventory.security.AppRole;
import com.chepogi.inventory.security.AppUser;
import com.chepogi.inventory.security.JwtProvider;
import com.chepogi.inventory.service.AuthService;
import com.chepogi.inventory.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public void signup(RegisterRequest registerRequest) {
        AppRole role = appRoleRepository.findById(registerRequest.getRole())
                .orElseThrow(() -> new AppRoleException("There is no such role"));

        AppUser user = new AppUser();
        user.setUsername(registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(registerRequest.isEnabled());
        user.setRole(role);

        appUserRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                .token(token)
                .username(loginRequest.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .username(refreshTokenRequest.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .build();
    }


}
