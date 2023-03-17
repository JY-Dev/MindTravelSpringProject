package com.jydev.mindtravel.auth.repository;

import com.jydev.mindtravel.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisRefreshTokenRepository implements RefreshTokenRepository {
    private final RedisTemplate<String,String> redisTemplate;
    @Override
    public void saveRefreshToken(String key, String refreshToken) {
        redisTemplate.opsForList().leftPush(key,refreshToken);
    }

    @Override
    public String findRefreshToken(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }
}
