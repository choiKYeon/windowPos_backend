package com.example.windowPos.redis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class RedisDto {
    private String key;
    private String value;
    private Duration duration;

    private static final Duration DEFAULT_DURATION = Duration.ofMinutes(30);

    public RedisDto(String key, String value, Duration duration) {
        this.key = key;
        this.value = value;
        this.duration = duration != null ? duration : DEFAULT_DURATION;
    }

    public RedisDto(String key, String value) {
        this.key = key;
        this.value = value;
        this.duration = DEFAULT_DURATION;
    }
}

