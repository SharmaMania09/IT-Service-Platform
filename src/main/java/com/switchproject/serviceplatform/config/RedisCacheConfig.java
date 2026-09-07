package com.switchproject.serviceplatform.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisCacheConfig
{
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory)
    {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                                                                    .entryTtl(Duration.ofSeconds(300))
                                                                    .serializeValuesWith(
                                                                        RedisSerializationContext.SerializationPair
                                                                            .fromSerializer(
                                                                                RedisSerializer.json())
                                                                    );

        return RedisCacheManager.builder(redisConnectionFactory)
                                .cacheDefaults(cacheConfig)
                                .build();
    }
}