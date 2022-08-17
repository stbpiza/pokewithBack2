package com.pokewith.user.dto;

import com.pokewith.user.User;
import com.pokewith.user.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class AuthUserDto implements UserDetails {

    // 시큐리티용 dto

    private Long userId;

    private String oauth2Id;

    private String email;

    private UserType userType;

    private String nickname1;


    @Builder
    public AuthUserDto(User user) {
        this.userId = user.getUserId();
        this.oauth2Id = user.getOauth2Id();
        this.email = user.getEmail();
        this.userType = user.getUserType();
        this.nickname1 = user.getNickname1();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add(this.userType.toString());
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() { return Long.toString(userId); }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
