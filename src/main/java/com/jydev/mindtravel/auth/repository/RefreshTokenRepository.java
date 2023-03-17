package com.jydev.mindtravel.auth.repository;

public interface RefreshTokenRepository {
    void saveRefreshToken(String key ,String refreshToken);
    String findRefreshToken(String key);
}
