package com.example.windowPos.global.filter;

import com.example.windowPos.global.config.SecurityUser;
import com.example.windowPos.global.jwt.JwtProvider;
import com.example.windowPos.global.jwt.JwtUtill;
import com.example.windowPos.global.rq.Rq;
import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.service.MemberService;
import com.example.windowPos.redis.service.RedisService;
import com.example.windowPos.redis.service.RedisServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Map;

// 스프링 시큐리티에 내장되어 있는 필터. (HTTP 요청이 들어올때마다 사용자의 토큰을 검사하는 구문)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Rq rq;
    private final JwtUtill jwtUtil;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final RedisServiceImpl redisServiceImpl;
    private final RedisService redisService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String path = request.getRequestURI();
        String token = extractAccessToken(request);

        if (token != null) {
//            String token = bearerToken.substring("Bearer ".length());

            if (jwtProvider.verify(token)) {
                Map<String, Object> claims = jwtProvider.getClaims(token);
                String username = (String) claims.get("username");

                Member member = memberService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
                forceAuthentication(member);
            }
        }

        //  "/api/v1/member/login" 경로를 제외
        if ("/api/v1/member/login".equals(path)) {
            filterChain.doFilter(request, response);
            return; // 여기서 메소드 종료
        }

        String accessToken = rq.getCookieValue("accessToken", null);

        if (accessToken != null && !jwtProvider.verify(accessToken)) {

//            accessToken이 만료된 경우, refreshToken 검증 및 새 accessToken 발급
            String refreshToken = rq.getCookieValue("refreshToken", null);

//            장시간 행동이 없어서 refreshToken과 accessToken이 만료 혹은 refreshToken이 만료됐을 때
            if ((!jwtProvider.verify(accessToken) && !jwtProvider.verify(refreshToken)) || !jwtProvider.verify(refreshToken)) {
                rq.removeCookie("accessToken");
                rq.removeCookie("refreshToken");
                redisServiceImpl.deleteValue(refreshToken);
            }

//            새로운 accessToken 생성
            String accessUsername = jwtProvider.getUsername(refreshToken);
            String newAccessToken = jwtUtil.genAccessToken(accessUsername);
            rq.setCrossDomainCookie("accessToken", newAccessToken);

//            새로 발급한 토큰으로 사용자 정보 조회 및 인증 설정
            SecurityUser securityUser = jwtUtil.getUserFromAccessToken(newAccessToken);
            rq.setLogin(securityUser);

//                refreshToken 토큰이 유효할 경우에
            String username = jwtProvider.getUsername(refreshToken);

                if (refreshToken != null && jwtProvider.verify(refreshToken)) {

//                    새로운 refreshToken 생성
                    String newRefreshToken = jwtUtil.genRefreshToken(username);

//                    기존 refreshToken, accessToken 삭제
                    redisServiceImpl.deleteValue(accessToken);
                    redisServiceImpl.deleteValue(refreshToken);
//                    redis에 새로운 access, refresh 토큰들 저장
                    memberService.saveRefreshToken(newRefreshToken, username);
                    memberService.saveAccessToken(newAccessToken, username);

//                    새 토큰 쿠키에 저장
                    rq.setCrossDomainCookie("refreshToken", newRefreshToken);
                }
        } else if (accessToken != null && jwtProvider.verify(accessToken)) {
//            accessToken이 유효한 경우
            SecurityUser securityUser = jwtUtil.getUserFromAccessToken(accessToken);
            rq.setLogin(securityUser);
        }

        filterChain.doFilter(request, response);
    }

    // 강제로 로그인 처리하는 메소드
    private void forceAuthentication(Member member) {
        User user = new User(member.getUsername(), member.getPassword(), member.getAuthorities());

        // 스프링 시큐리티 객체에 저장할 authentication 객체를 생성
        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        user,
                        null,
                        member.getAuthorities()
                );

        // 스프링 시큐리티 내에 우리가 만든 authentication 객체를 저장할 context 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // context에 authentication 객체를 저장
        context.setAuthentication(authentication);
        // 스프링 시큐리티에 context를 등록
        SecurityContextHolder.setContext(context);
    }

    public static String extractAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}