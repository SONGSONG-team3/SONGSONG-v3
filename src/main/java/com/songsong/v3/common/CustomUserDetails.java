package com.songsong.v3.common;

import com.songsong.v3.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 사용자 정보 저장
 * 사용자 정보(이메일, 비밀번호, 권한 등)를 제공한다.
 * 인증이 성공하면 spring security 에서 해당 클래스에 저장된 사용자 정보를 바탕으로 권한을 확인한다.
 * 사용자 이메일과 비밀번호를 사용해 인증하고, 인증 성공 시 Spring Security 가 UserDetails 를 이용해 사용자 권한을 확인하고 처리한다.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(user::getRole);
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();
    }

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
