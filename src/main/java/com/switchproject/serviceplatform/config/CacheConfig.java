package com.switchproject.serviceplatform.config;

import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig implements CachingConfigurer {

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }
}