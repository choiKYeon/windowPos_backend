package com.example.windowPos;

import com.example.windowPos.global.jwt.JwtUtill;
import com.example.windowPos.global.rs.RsData;
import com.example.windowPos.global.util.Util;
import com.example.windowPos.global.utill.CookieUtils;
import com.example.windowPos.member.controller.MemberController;
import com.example.windowPos.member.dto.MemberDto;
import com.example.windowPos.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional  // 각각의 테스트 메서드에 대해 트랜잭션을 시작하고, 테스트가 종료되면 롤백
class WindowPosApplicationTests {

//    임시 랜덤 포트 배정
    @LocalServerPort
    private int port;

//    실제 http 요청을 보내기 위함
    @Autowired
    private TestRestTemplate restTemplate;

//    가짜객체를 생성하기 위한 Mock 사용
    @Mock
    private MemberService memberService;

    @Mock
    private JwtUtill jwtUtil;

    @Mock
    private CookieUtils cookieUtils;

    @InjectMocks
    private MemberController memberController;

    @Mock
    private HttpServletRequest req;

    //	기본인증 자격증명 안하면 로그인 테스트시 401에러뜸
    @BeforeEach
    void setUp() {
        restTemplate = restTemplate.withBasicAuth("user", "password");
    }

    @Test
    void testLoginAndLogout() throws Exception {

        String username = "gysoft";
        String password = "1234";
        String accessToken = "testAccessToken";
        String refreshToken = "testRefreshToken";

        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(username);
        memberDto.setPassword(password);

//        실제 값을 반환하는 대신 true를 반환
        when(memberService.memberCheck(username, password)).thenReturn(true);
        when(jwtUtil.genAccessToken(username)).thenReturn(accessToken);
        when(jwtUtil.genRefreshToken(username)).thenReturn(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<MemberDto> entity = new HttpEntity<>(memberDto, headers);

        // 로그인 요청
        ResponseEntity<RsData> loginResponse = restTemplate.exchange(
                createURLWithPort("/api/v1/member/login"),
                HttpMethod.POST, entity, RsData.class);

        // 응답이 null인지 확인
//        assertThat(loginResponse).isNotNull();

        RsData loginResponseBody = loginResponse.getBody();

        // 응답 객체 출력
        System.out.println(loginResponseBody);

        assertThat(loginResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(loginResponseBody.getResultCode()).isEqualTo("S-1");
        assertThat(loginResponseBody.getMsg()).isEqualTo("로그인 성공");
        assertThat(loginResponseBody.getData()).isNotNull();

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        // HttpServletRequest의 getCookies() 메서드를 모킹하여 Cookie 배열 반환
        when(req.getCookies()).thenReturn(new Cookie[]{accessTokenCookie, refreshTokenCookie});

        Optional<Cookie> accessTokenOptional = CookieUtils.getCookie(req, "accessToken");
        Optional<Cookie> refreshTokenOptional = CookieUtils.getCookie(req, "refreshToken");

//        로그아웃해주세연
        ResponseEntity<RsData> logoutResponse = restTemplate.exchange(
                createURLWithPort("/api/v1/member/logout"),
                HttpMethod.POST, null, RsData.class);
//오류 잡아야함
        RsData logoutResponseBody = logoutResponse.getBody();
        assertThat(logoutResponseBody).isNotNull();

        assertThat(logoutResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(logoutResponseBody.getResultCode()).isEqualTo("S-1");
        assertThat(logoutResponseBody.getMsg()).isEqualTo("로그아웃 성공");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
