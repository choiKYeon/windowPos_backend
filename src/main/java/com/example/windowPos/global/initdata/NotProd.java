package com.example.windowPos.global.initdata;

import com.example.windowPos.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return args -> {
//            Member admin = Member.builder()
//                    .name("관리자")
//                    .username("gysoft")
//                    .password(passwordEncoder.encode("1234"))
//                    .createDate(LocalDateTime.now())
//                    .build();
//
//            memberRepository.save(admin);
        };
    }
}