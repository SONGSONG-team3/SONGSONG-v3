package com.songsong.v3.common;

import com.songsong.v3.user.dto.UserLoginRequestDto;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${spring.jwt.secret}")
    private String secretKeyStr = "secretKey";

    private final long tokenValidMillisecond = 1000L * 60 * 60; // 1시간 토큰 유효

    // SecretKey 인코딩
    private SecretKey secretKey;
    @PostConstruct
    protected void init() {
        LOGGER.info("[JwtTokenProvider init] secretKey 초기화 시작");
        System.out.println(secretKeyStr);
        secretKey = new SecretKeySpec(secretKeyStr.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        System.out.println(secretKey);
        LOGGER.info("[JwtTokenProvider init] secretKey 초기화 완료");
    }

    // JWT 토큰에서 이메일 추출
    public String getUserEmail(String token) {
        LOGGER.info("[JwtTokenProvider getUserEmail] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
        LOGGER.info("[JwtTokenProvider getUserEmail] 토큰 기반 회원 구별 정보 추출 완료: " + info);
        return info;
    }

    // 로그인 성공시, successful handler 를 통해 username, role, expired 전달받아 토큰 생성 JWT 토큰 생성
    public String createToken(UserLoginRequestDto user) {
        LOGGER.info("[JwtTokenProvider createToken] 토큰 생성 시작");

        String token = Jwts.builder()
                .claim("email", user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMillisecond))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

        LOGGER.info("[JwtTokenProvider createToken] 토큰 생성 완료");
        return token;

    }

    // JWT 토큰의 유효성 + 만료일 체크
    public boolean validateToken(String token) {
        LOGGER.info("[JwtTokenProvider validateToken] 토큰 유효성 체크 시작");
        try {
            return !Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token).getPayload()
                    .getExpiration().before(new Date());
        } catch (Exception e) {
            LOGGER.info("[JwtTokenProvider validateToken] 토큰 유효성 체크 예외 발생");
            return false;
        }
    }

}
