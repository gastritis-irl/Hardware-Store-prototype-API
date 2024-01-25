package edu.bbte.idde.bfim2114.springbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration
public class CacheConfig {

    private static final Duration DEFAULT_TTL = Duration.ofMinutes(60);

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(DEFAULT_TTL)
            .disableCachingNullValues();
    }
}
