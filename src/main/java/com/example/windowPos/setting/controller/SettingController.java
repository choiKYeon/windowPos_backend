package com.example.windowPos.setting.controller;

import com.example.windowPos.global.rs.RsData;
import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.repository.MemberRepository;
import com.example.windowPos.setting.dto.SettingDto;
import com.example.windowPos.setting.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/setting")
public class SettingController {

    private final SettingService settingService;
    private final MemberRepository memberRepository;

//    세팅 정보 불러오기
    @GetMapping
    public ResponseEntity<RsData<SettingDto>> getSetting() {
//        현재 로그인한 녀석
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Member member = memberRepository.findByUsername(username).orElse(null);

        SettingDto settingDto = settingService.getSettingByMember(member);
        return ResponseEntity.ok(RsData.of("S-1", "세팅 불러오기 성공", settingDto));
    }

//    세팅 업데이트 하는 구문 (수정중)
    @PutMapping(value = "/{id]")
    public ResponseEntity<RsData<?>> updateSetting(@PathVariable("id") Integer id, @RequestBody SettingDto settingDto) {

    }
}
