package com.jydev.mindtravel.domain.auth.repository;

public interface RefreshTokenRepository {
    void saveRefreshToken(String key, String refreshToken);

    String findRefreshToken(String key);

    void deleteRefreshToken(String key);
}
