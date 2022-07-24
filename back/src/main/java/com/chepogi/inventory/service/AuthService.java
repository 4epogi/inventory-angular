package com.chepogi.inventory.service;

import com.chepogi.inventory.dto.AuthenticationResponse;
import com.chepogi.inventory.dto.LoginRequest;
import com.chepogi.inventory.dto.RefreshTokenRequest;
import com.chepogi.inventory.dto.RegisterRequest;

public interface AuthService {

    public void signup(RegisterRequest registerRequest);

    AuthenticationResponse login(LoginRequest loginRequest);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
