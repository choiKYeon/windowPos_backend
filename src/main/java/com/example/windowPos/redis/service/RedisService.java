package com.example.windowPos.redis.service;

import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public interface RedisService {
    //    값 등록
    void setValues(String key, String value);

    //    등록 , 수정
    void setValues(String key, String value, Duration duration);

    //    값 조회
    String getValue(String key);

    //    값 삭제
    void deleteValue(String key);
}
