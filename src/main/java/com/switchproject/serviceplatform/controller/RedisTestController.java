package com.switchproject.serviceplatform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.switchproject.serviceplatform.service.RedisTestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/redis")
public class RedisTestController
{
    private final RedisTestService redisTestService;

    public RedisTestController(RedisTestService redisTestService)
    {
        this.redisTestService = redisTestService;
    }

    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value)
    {
        redisTestService.setValue(key, value);

        return "Value is saved in Redis";
    }

    @PostMapping("/set-ttl")
    public String setValueWithTtl(@RequestParam String key, @RequestParam String value)
    {
        redisTestService.setValueWithTTL(key, value);

        return "Value is saved in Redis with TTL !!!!";
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key)
    {
        return redisTestService.getValue(key);
    }
    
    @DeleteMapping("/delete")
    public String deleteValue(@RequestParam String key)
    {
        redisTestService.deleteValue(key);
        return "Value deleted from Redis";
    }
}