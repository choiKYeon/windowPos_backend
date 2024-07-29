package com.example.windowPos.member.controller;

import com.example.windowPos.global.jwt.JwtProvider;
import com.example.windowPos.global.jwt.JwtUtill;
import com.example.windowPos.global.rq.Rq;
import com.example.windowPos.global.rs.RsData;
import com.example.windowPos.member.dto.MemberDto;
import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.service.MemberService;
import com.example.windowPos.redis.service.RedisServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.windowPos.global.filter.JwtAuthenticationFilter.extractAccessToken;
import static com.example.windowPos.global.filter.JwtAuthenticationFilter.extractRefreshToken;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;
    private final RedisServiceImpl redisServiceImpl;
    private final JwtProvider jwtProvider;
    private final JwtUtill jwtUtill;
    private final Rq rq;

    @Getter
    public static class LoginResponse {

        private String accessToken;
        private String refreshToken;

        public LoginResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    //      로그인
    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<RsData<LoginResponse>> login(@RequestBody MemberDto memberDto, @RequestParam(required = false) boolean rememberMe, HttpServletResponse response) throws Exception {
        boolean loginMember = memberService.memberCheck(memberDto.getUsername(), memberDto.getPassword());

        if (loginMember) {
            String accessToken = jwtUtill.genAccessToken(memberDto.getUsername());
            String refreshToken = jwtUtill.genRefreshToken(memberDto.getUsername());

            memberService.saveRefreshToken(refreshToken, memberDto.getUsername());
            memberService.saveAccessToken(accessToken, memberDto.getUsername());

            if (rememberMe) {
                // 토큰 저장 (브라우저가 꺼지거나 컴퓨터가 종료되어도 삭제되지 않음. 유효기간이 유지됨.)
                rq.setCrossDomainCookie("accessToken", accessToken, 60 * 30);
                rq.setCrossDomainCookie("refreshToken", refreshToken, 60 * 60 * 24 * 365 * 10); // 10년
                rq.setCrossDomainCookie("rememberMe", "true", 60 * 60 * 24 * 365 * 10);
            } else {
                // 세션 쿠기 설정 (세션 쿠키는 브라우저가 종료되거나 컴퓨터가 꺼지면 삭제됨.)
                rq.setCrossDomainCookie("accessToken", accessToken, -1);
                rq.setCrossDomainCookie("refreshToken", refreshToken, -1);
                rq.setCrossDomainCookie("rememberMe", "false", -1);
            }

            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);

            return ResponseEntity.ok(RsData.of("S-1", "로그인 성공", loginResponse));
        } else {
            return ResponseEntity.ok(RsData.of("일치하지 않음", null));
        }
    }

    //    자동 로그인


    @PostMapping(value = "/logout")
    public ResponseEntity<RsData<LoginResponse>> logout(HttpServletRequest req) {
        String refreshTokenKey = extractRefreshToken(req);
        String accessTokenKey = extractAccessToken(req);

        String refreshToken = "refreshToken :" + refreshTokenKey;
        String accessToken = "accessToken :" + accessTokenKey;

        redisServiceImpl.deleteValue(accessToken);
        redisServiceImpl.deleteValue(refreshToken);

        rq.removeCookie("accessToken");
        rq.removeCookie("refreshToken");

        return ResponseEntity.ok(RsData.of("S-1", "로그아웃 성공", null));
    }


    //    현재 로그인한 유저
    @AllArgsConstructor
    @Getter
    public static class loginUser {
        private final Member member;
    }

    @GetMapping(value = "/loginUser", consumes = APPLICATION_JSON_VALUE)
    public RsData<?> loginUser(HttpServletRequest request) {
        String token = extractAccessToken(request); //헤더에 담긴 쿠키에서 토큰 요청

        Long userId = ((Integer) jwtProvider.getClaims(token).get("id")).longValue(); //유저의 아이디 값

        Member loginUser = this.memberService.findById(userId).orElse(null);
        return RsData.of("S-1", "현재 로그인 유저", new loginUser(loginUser));
    }

}
