package com.songsong.v3.config;

import com.songsong.v3.common.CustomAccessDeniedHandler;
import com.songsong.v3.common.CustomAuthenticationEntryPoint;
import com.songsong.v3.user.service.CustomUserDetailsService;
import com.songsong.v3.common.JwtAuthenticationFilter;
import com.songsong.v3.common.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/signup",              // Thymeleaf 회원가입 페이지
                                "index.html",
                                "/assets/**",
                                "/api/v3/users/**"
                                ).permitAll()

                        .anyRequest().authenticated())

                // JWT를 통한 인증/인가를 위해서 세션을 STATELESS 상태로 설정
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler()) // 권한 예외 발생 처리
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 실패시 처리
                );
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

