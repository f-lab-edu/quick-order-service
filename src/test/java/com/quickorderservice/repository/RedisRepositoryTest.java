package com.quickorderservice.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RedisRepositoryTest {

    @Autowired RedisRepository redisRepository;

    @Test
    @DisplayName("redis에 값을 정상적으로 get set 해보기")
    public void setAnsget() {
        String key = "key";
        String value = "value";
        redisRepository.set(key, value);
        String redisValue = (String) redisRepository.get(key);

        Assertions.assertThat(value).isEqualTo(redisValue);
    }

    @Test
    @DisplayName("redis에 있는 값을 지우고 get을 하면 null을 반환한다.")
    public void delete() {
        String key = "key";
        String value = "value";
        redisRepository.set(key, value);
        redisRepository.remove(key);
        String redisValue = (String) redisRepository.get(key);

        Assertions.assertThat(redisValue).isNull();
    }

    @Test
    @DisplayName("redis에 없는 값을 get을 하면 null을 반환한다.")
    public void set2() {
        String key = "key";
        String redisValue = (String) redisRepository.get(key);

        Assertions.assertThat(redisValue).isNull();
    }
}