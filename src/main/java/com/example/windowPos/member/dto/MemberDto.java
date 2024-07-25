package com.example.windowPos.member.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto{

    private Long id;

    private String name;

    private String username;

    private String password;

    //    권한
    private Collection<? extends GrantedAuthority> authorities;

    //    토큰 생성 정보
    public Map<String, Object> toClaims() {
        return Map.of(
                "id", getId(),
                "name", getName(),
                "username", getUsername(),
                "authorities", getAuthorities()
        );
    }
}
