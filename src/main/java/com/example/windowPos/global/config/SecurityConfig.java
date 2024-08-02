package com.example.windowPos.global.config;

import com.example.windowPos.global.customUserDetailService.CustomUserDetailsService;
import com.example.windowPos.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    private final CustomUserDetailsService customUserDetailsService;
    private final BaseConfig baseConfig;

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
                .formLogin(formLogin -> formLogin.loginPage("/api/v1/member/login")
                        .permitAll())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(STATELESS)
                ) // 세션끄기
                .addFilterBefore( // jwy 필터 적용
                        jwtAuthenticationFilter, // 엑세스 토큰으로 부터 로그인 처리
                        UsernamePasswordAuthenticationFilter.class
                )
        ;

        return http.build();
    }

//    CustomUserDetailsService를 시큐리티에서 사용하기 위해 설정
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(baseConfig.passwordEncoder());
        return daoAuthenticationProvider;
    }

    //    시큐리티 인증
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
