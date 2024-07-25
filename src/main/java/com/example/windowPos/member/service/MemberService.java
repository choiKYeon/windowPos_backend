package com.example.windowPos.member.service;


import com.example.windowPos.global.encrypt.EncryptionUtils;
import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.repository.MemberRepository;
import com.example.windowPos.redis.service.RedisService;
import com.example.windowPos.redis.service.RedisServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final RedisService redisService;
    private final RedisServiceImpl redisServiceImpl;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //    유저 아이디를 통해 member를 찾는 구문 (Dto값을 반환)
    public Optional<Member> findByUsername(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);
        return Optional.ofNullable(member);
//        return memberRepository.findByUsername(username)
//                .map(member -> new MemberDto(member.getName(), member.getUsername(), member.getEmail()));
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
    public void saveRefreshToken (String refreshToken, String username){

        Duration refreshTokenDuration = Duration.ofDays(7);
        redisService.setValues(refreshToken, username, refreshTokenDuration);
    }

//    로그인 시 해당 유저의 accessToken을 저장하는 구문
    public void saveAccessToken (String accessToken, String username){

        Duration accessTokenDuration = Duration.ofMinutes(30);
        redisService.setValues(accessToken, username, accessTokenDuration);
    }

//    Id로 유저 조회
    public Optional<Member> findById(Long id) {
        return this.memberRepository.findById(id);
    }
}
