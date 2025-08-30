package com.tikam.bankingSystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testRedis() {
        redisTemplate.opsForValue().set("email", "tikamsuvasiya344@gmail.com");
        Object var = redisTemplate.opsForValue().get("email");
        int a = 1;

    }


}