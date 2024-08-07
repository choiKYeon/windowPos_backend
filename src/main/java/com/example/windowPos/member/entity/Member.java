package com.example.windowPos.member.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.example.windowPos.setting.entity.Setting;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Member extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));

        if (isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("gysoft"));
        }

        return authorities;
    }
    public boolean isAdmin() {
        return "gysoft".equals(username);
    }

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Setting setting = new Setting();

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
