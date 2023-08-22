package com.jydev.mindtravel.domain.auth.repository;

import com.jydev.mindtravel.external.redis.RedisManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisRefreshTokenRepository implements RefreshTokenRepository {
    private final RedisManager redisManager;

    @Override
    public void saveRefreshToken(String key, String refreshToken) {
        redisManager.save(RedisManager.Domain.JWT, key, refreshToken);
    }

    @Override
    public String findRefreshToken(String key) {
        return redisManager.get(RedisManager.Domain.JWT, key);
    }

    @Override
    public void deleteRefreshToken(String key) {
        redisManager.delete(RedisManager.Domain.JWT, key);
    }
}
