package com.skymind.backend.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.*;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {

        CaffeineCacheManager manager = new CaffeineCacheManager("flightSearchCache","flights");
        manager.setCaffeine(Caffeine.newBuilder()
                        .expireAfterWrite(60, TimeUnit.MINUTES).maximumSize(200));

        return manager;
    }
}