package com.example.windowPos.global.config;

import com.example.windowPos.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final CustomUserDetailsService customUserDetailsService;
    private final BaseConfig baseConfig;

    /** 비동기 요청 - 응답시 사용.
     * 입력된 HTTP 요청의 사용자 자격 증명 값을 추출하기 위한 UsernamePasswordAuthenticationFilter의 구현체
     * 추출한 값으로 UsernamePasswordAuthenticationToken 미인증 상태 객체를 생성해 AuthenticationManager에게 전달.
     */
//    @Bean
//    public JsonAuthenticationFilter jsonAuthenticationFilter() {
//        JsonAuthenticationFilter jsonFilter = new JsonAuthenticationFilter(new ObjectMapper());
//        jsonFilter.setAuthenticationManager(authenticationManager());
//        return jsonFilter;
//    }
//
//    /*
//    미인증 UsernamePasswordAuthenticationToken 객체를 받아, 객체에 포함되어 있는 자격 증명이 유효한지 검사
//    DaoAuthenticationProvider는 내부적으로 설정된 UserDetailsService의 loadUserByUsername 메소드를 호출하여 UserDetails 객체를 가져옴
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(baseConfig.passwordEncoder());
//        return new ProviderManager(daoAuthenticationProvider);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 아래의 모든 설정은 /api/** 경로에만 적용
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers("/**").permitAll() // 로그인은 누구나 가능
                                .anyRequest().authenticated() // 나머지는 인증된 사용자만 가능
                        // 나머지는 인증된 사용자만 가능
                )
                .cors(cors -> cors.configure(http)) // 타 도메인에서 API 호출 가능
                .csrf(csrf -> csrf.disable()) // CSRF 토큰 끄기
                .httpBasic(httpBasic -> httpBasic.disable()) // httpBaic 로그인 방식 끄기
                .formLogin(formLogin -> formLogin.disable()) // 동기 요청 작업 비활성화
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(STATELESS)
                ) // 세션끄기
                .addFilterBefore( // jwt 필터 적용
                        jwtAuthenticationFilter, // 엑세스 토큰으로 부터 로그인 처리
                        UsernamePasswordAuthenticationFilter.class
                )
        ;

        return http.build();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}