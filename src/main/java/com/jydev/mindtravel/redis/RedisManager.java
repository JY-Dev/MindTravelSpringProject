package com.jydev.mindtravel.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisManager {
    private final RedisTemplate<String,String> redisTemplate;

    public void save(Domain domain, String key, String value){
        redisTemplate.opsForValue().set(domain.getDomainKey(key),value);
    }

    public String get(Domain domain, String key){
        return redisTemplate.opsForValue().get(domain.getDomainKey(key));
    }

    public void delete(Domain domain, String key){
        redisTemplate.delete(domain.getDomainKey(key));
    }

    public enum Domain{
        JWT("jwt");
        private final String keyPostFix;
        Domain(String keyPostFix){
            this.keyPostFix = keyPostFix;
        }
        public String getDomainKey(String key){
            return key+keyPostFix;
        }
    }
}
