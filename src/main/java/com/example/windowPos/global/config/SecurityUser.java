package com.example.windowPos.global.config;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

// 세밀한 사용자 관리 및 보안 정책을 구현하기 위한 코드
public class SecurityUser extends User {
    @Getter
    private long id;
// 여기에 토큰을 복호화 시켜서 해당 유저 정보를 담아야함.
    public SecurityUser(long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public Authentication genAuthentication() {
// 스프링 시큐리티 내에서 사용자명, 비밀번호, 권한 목록 등 을 받아 객체를 생성하는 구문
        Authentication auth = new UsernamePasswordAuthenticationToken(
                this,
                this.getPassword(),
                this.getAuthorities()
        );

        return auth;
    }
}
