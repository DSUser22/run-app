package com.dasha.usersystem.refreshToken;

import com.dasha.usersystem.appuser.AppUserRepo;
import com.dasha.usersystem.exception.login.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${app.refreshTokenExpirationDays}")
    private Long refreshTokenExpirationDays;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    @Autowired
    private AppUserRepo appUserRepo;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAppUser(appUserRepo.findById(userId).get());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(refreshTokenExpirationDays));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException("Refresh token was expired. Please login again");
        }
        return token;
    }

    @Transactional
    public void deleteByAppUserId(Long userId) {
        refreshTokenRepo.deleteByAppUserId(userId);
    }
    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepo.deleteByToken(token);
    }

}
