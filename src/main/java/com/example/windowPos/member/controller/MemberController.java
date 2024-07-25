package com.example.windowPos.member.controller;

import com.example.windowPos.global.jwt.JwtProvider;
import com.example.windowPos.global.jwt.JwtUtill;
import com.example.windowPos.global.rq.Rq;
import com.example.windowPos.global.rs.RsData;
import com.example.windowPos.member.dto.MemberDto;
import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.service.MemberService;
import com.example.windowPos.redis.service.RedisServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(value = "/login" , consumes = APPLICATION_JSON_VALUE)
    public RsData<LoginResponse> login(@RequestBody MemberDto memberDto) throws Exception {
        boolean loginMember = memberService.memberCheck(memberDto.getUsername(), memberDto.getPassword());

        if (loginMember) {
            String accessToken = jwtUtill.genAccessToken(memberDto.getUsername());
            String refreshToken = jwtUtill.genRefreshToken(memberDto.getUsername());

            memberService.saveRefreshToken(refreshToken, memberDto.getUsername());
            memberService.saveAccessToken(accessToken, memberDto.getUsername());

            rq.setCrossDomainCookie("accessToken", accessToken);
            rq.setCrossDomainCookie("refreshToken", refreshToken);

            return RsData.of("S-1", "토큰이 생성되었습니다.", null);
        }  else {
            return RsData.of("일치하지 않음", null);
        }
    }

    @PostMapping(value = "/logout", consumes = APPLICATION_JSON_VALUE)
    public RsData<LoginResponse> logout(HttpServletRequest req) {
        String refreshToken = extractRefreshToken(req);
        String accessToken = extractAccessToken(req);

        redisServiceImpl.deleteValue(accessToken);
        redisServiceImpl.deleteValue(refreshToken);

        rq.removeCookie("accessToken");
        rq.removeCookie("refreshToken");

        return RsData.of("S-1", "토큰이 삭제되었습니다.", null);
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
