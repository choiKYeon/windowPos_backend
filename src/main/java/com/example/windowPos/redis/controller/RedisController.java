package com.example.windowPos.redis.controller;

import com.example.windowPos.redis.dto.RedisDto;
import com.example.windowPos.redis.service.RedisService;
import com.example.windowPos.global.rs.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/redis")
public class RedisController {

    private final RedisService redisService;

    //    값 조회
    @GetMapping("/getToken")
    public RsData<Object> getToken(@RequestBody RedisDto redisDto) {
        String result = redisService.getValue(redisDto.getKey());

        return RsData.of("S-1", "값 조회 성공", result);
    }

    //    값 수정 / 추가
    @PostMapping("/setToken")
    public RsData<Object> setToken(@RequestBody RedisDto redisDto) {

        if (redisDto.getDuration() == null) {
            redisService.setValues(redisDto.getKey(), redisDto.getValue());
        } else {
            redisService.setValues(redisDto.getKey(), redisDto.getValue(), redisDto.getDuration());
        }

        return RsData.of("S-1", "값 추가 / 수정 성공");
    }

    //    값 삭제
    @DeleteMapping("/deleteToken")
    public RsData<Object> deleteToken(@RequestBody RedisDto redisDto) {
        redisService.deleteValue(redisDto.getKey());
        return RsData.of("S-1", "값 삭제 성공");
    }
}
