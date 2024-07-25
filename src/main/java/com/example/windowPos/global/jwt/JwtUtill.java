package com.example.windowPos.global.jwt;

import com.example.windowPos.global.config.SecurityUser;
import com.example.windowPos.global.encrypt.EncryptionUtils;
import com.example.windowPos.global.util.Util;
import com.example.windowPos.member.dto.MemberDto;
import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Lazy
@Component
@RequiredArgsConstructor
public class JwtUtill {
    private final EncryptionUtils encryptionUtils;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    //    토큰 발급 구문
    public String genAccessToken(String username) {
        Member member = memberService.findByUsername(username).orElse(null);
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setUsername(member.getUsername());
        memberDto.setName(member.getName());
        memberDto.setAuthorities(member.getAuthorities());

        if (memberDto == null) return null;

        return jwtProvider.genToken(memberDto.toClaims(), 60 * 30);
    }

    public String genRefreshToken(String username) {
        Member member = memberService.findByUsername(username).orElse(null);
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setUsername(member.getUsername());
        memberDto.setName(member.getName());
        memberDto.setAuthorities(member.getAuthorities());

        if (memberDto == null) return null;

        return jwtProvider.genToken(memberDto.toClaims(), 60 * 60 * 24 * 7);
    }

    //    토큰으로 사용자 정보 추출해서 SecurityUser객체를 생성하는 구문
    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = getDataFrom(accessToken);
        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<Map<String, String>> authoritiesList = (List<Map<String, String>>) payloadBody.get("authorities");

//        json 문자열을 파싱하는 구문
        List<SimpleGrantedAuthority> authorities = authoritiesList.stream()
                .map(authMap -> new SimpleGrantedAuthority(authMap.get("authority")))
                .collect(Collectors.toList());

        return new SecurityUser(
                id,
                username,
                "",
                authorities
        );
    }

    //    jwt 토큰의 페이로드에서 필요한 데이터 추출하는 구문
    public Map<String, Object> getDataFrom(String token) {
        Claims payload = Jwts.parser()
                .setSigningKey(encryptionUtils.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();

        // JSON 문자열을 파싱합니다.
        String bodyJson = payload.get("body", String.class);
        Map<String, Object> bodyClaims = Util.toMap(bodyJson);

        return Map.of(
                "id", bodyClaims.get("id"),
                "name", bodyClaims.get("name"),
                "username", bodyClaims.get("username"),
                "authorities", bodyClaims.get("authorities")
        );
    }
}
