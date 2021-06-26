package com.quickorderservice.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public Object get(String key) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        return vop.get(key);
    }

    public void set(String key, Object value) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(key, value);
    }

    public void remove(String key) {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(key,null);
    }

}
