package com.switchproject.serviceplatform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class CustomCacheErrorHandler implements CacheErrorHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomCacheErrorHandler.class);

    @Override
    public void handleCacheGetError(
            RuntimeException exception,
            Cache cache,
            Object key) {

        logger.warn(
                "Redis cache GET failed for key: {}. Continuing without cache.",
                key
        );
    }

    @Override
    public void handleCachePutError(
            RuntimeException exception,
            Cache cache,
            Object key,
            Object value) {

        logger.warn(
                "Redis cache PUT failed for key: {}. Continuing without cache.",
                key
        );
    }

    @Override
    public void handleCacheEvictError(
            RuntimeException exception,
            Cache cache,
            Object key) {

        logger.warn(
                "Redis cache EVICT failed for key: {}.",
                key
        );
    }

    @Override
    public void handleCacheClearError(
            RuntimeException exception,
            Cache cache) {

        logger.warn(
                "Redis cache CLEAR failed."
        );
    }
}