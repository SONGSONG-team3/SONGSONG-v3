package com.songsong.v3.user.service;

import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.repository.UserRepository;
import com.songsong.v3.common.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 로그인 시 이메일로 사용자 조회
 * 1. 사용자의 이메일 기준으로 DB 에서 사용자를 찾는다.
 * 2. CustomerUserDetails 로 변환해서 반환한다.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(email);

        if (user != null) {
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("User not found with email: " + email);

    }
}
