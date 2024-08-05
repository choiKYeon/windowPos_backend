package com.example.windowPos.member.service;

import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.repository.MemberRepository;
import com.example.windowPos.redis.service.RedisService;
import com.example.windowPos.redis.service.RedisServiceImpl;
import com.example.windowPos.setting.entity.Setting;
import com.example.windowPos.setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final RedisService redisService;
    private final RedisServiceImpl redisServiceImpl;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SettingRepository settingRepository;

//    로그인 시 기본 세팅 설정
    @Transactional
    public Setting newMemberLogin(Member member) {
        Setting setting = settingRepository.findByMember(member).orElse(null);
        if (setting == null) {
            setting = new Setting();
        }
        setting.setMember(member);
        member.setSetting(setting);
        settingRepository.save(setting);
        memberRepository.save(member);
        return setting;
    }

    //    유저 아이디를 통해 member를 찾는 구문 (Dto값을 반환)
    public Optional<Member> findByUsername(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);
        return Optional.ofNullable(member);
    }

    //    유저를 확인하는 구문 (entity값을 반환)
    public boolean memberCheck(String username, String password) {
        Optional<Member> member = memberRepository.findByUsername(username);
        Member checkMember = member.orElse(null);

        if (checkMember != null) {
            return passwordEncoder.matches(password, checkMember.getPassword());
        } else {
            return false;
        }
    }

    //    로그인 시 해당 유저의 refreshToken을 저장하는 구문
    public void saveRefreshToken (String username, String refreshTokenKey){

        String refreshToken = refreshTokenKey;

        Duration refreshTokenDuration = Duration.ofDays(7);
        redisService.setValues("refreshToken", refreshToken , refreshTokenDuration);
    }

//    로그인 시 해당 유저의 accessToken을 저장하는 구문
    public void saveAccessToken (String username, String accessTokenKey){

        String accessToken = accessTokenKey;

        Duration accessTokenDuration = Duration.ofMinutes(30);
        redisService.setValues("accessToken", accessToken, accessTokenDuration);
    }

//    Id로 유저 조회
    public Optional<Member> findById(Long id) {
        return this.memberRepository.findById(id);
    }

//    현재 로그인한 회원을 가져오는 메서드
    public Member getCurrentMember() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
