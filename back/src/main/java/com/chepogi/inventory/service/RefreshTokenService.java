package com.chepogi.inventory.service;

import com.chepogi.inventory.model.RefreshToken;


public interface RefreshTokenService {
    public RefreshToken generateRefreshToken();
    public void validateRefreshToken(String token);
    public void deleteToken(String token);
}
