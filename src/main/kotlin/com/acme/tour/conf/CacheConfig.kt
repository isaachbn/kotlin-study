package com.acme.tour.conf

import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun cachemanager() = ConcurrentMapCacheManager(
            "promotions_all",
            "promotions_find_by_locale",
            "promotion_find_by_id"
    )
}