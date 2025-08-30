package com.tikam.bankingSystem.RedisService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tikam.bankingSystem.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();



    public <T> T  get(String key, Class<T> accountClass){
        String object = redisTemplate.opsForValue().get(key);
        try {
            T obj = objectMapper.readValue(object, accountClass);
            System.out.println("the value sotered in redis successfully");
            return obj;
        } catch (Exception e) {
            System.out.println("the get value is not get ");
            return null;
        }
    }

    public void setAccount(String key, Object o,Long ttl) {
        try {
            String json = objectMapper.writeValueAsString(o);

            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set("job", "engineer");
        } catch (Exception e) {
            System.out.println("the set operation is not executed due to error occurred");
        }
    }
}
