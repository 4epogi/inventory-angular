package com.chepogi.inventory.service.impl;

import com.chepogi.inventory.exceptions.InventoryException;
import com.chepogi.inventory.model.RefreshToken;
import com.chepogi.inventory.repository.RefreshTokenRepository;
import com.chepogi.inventory.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InventoryException("Didnt find such refresh token"));
    }

    @Override
    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
