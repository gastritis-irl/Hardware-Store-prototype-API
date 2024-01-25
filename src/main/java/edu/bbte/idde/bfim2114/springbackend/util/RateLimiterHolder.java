package edu.bbte.idde.bfim2114.springbackend.util;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;


public class RateLimiterHolder {
    private final ConcurrentHashMap<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();
    private final int maxRequestsPerMinute;


    public RateLimiterHolder(int maxRequestsPerMinute) {
        this.maxRequestsPerMinute = maxRequestsPerMinute;
    }

    public boolean isAllowed(String clientIp) {
        RateLimiter rateLimiter = rateLimiters.computeIfAbsent(clientIp, key -> createRateLimiter());
        // Non-blocking check
        return rateLimiter.tryAcquire();
    }

    private RateLimiter createRateLimiter() {
        // Convert permits per minute to permits per second as RateLimiterHolder works with permits per second
        return RateLimiter.create((double) maxRequestsPerMinute / 60.0);
    }
}
