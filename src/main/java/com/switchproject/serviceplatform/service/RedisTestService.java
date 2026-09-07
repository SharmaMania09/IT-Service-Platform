package com.switchproject.serviceplatform.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTestService
{
    private final StringRedisTemplate redisTemplate;

    public RedisTestService(StringRedisTemplate redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }
    
    public void setValue(String key, String value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValueWithTTL(String key, String value)
    {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(60));
    }

    public String getValue(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key)
    {
        redisTemplate.delete(key);
    }
}