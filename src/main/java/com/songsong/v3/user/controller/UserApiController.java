package com.songsong.v3.user.controller;

import com.songsong.v3.user.dto.UserLoginRequestDto;
import com.songsong.v3.user.dto.UserLoginResultDto;
import com.songsong.v3.user.dto.UserSignupRequestDto;
import com.songsong.v3.user.dto.UserSignupResultDto;
import com.songsong.v3.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/users/")
public class UserApiController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);
    private final UserService userService;

    // 1. 회원가입
    @PostMapping("/signup")
    public UserSignupResultDto signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        LOGGER.info("회원가입 시작: " + userSignupRequestDto.getUserDto().getUserName());
        UserSignupResultDto userSignupResultDto = userService.signup(userSignupRequestDto);
        LOGGER.info("회원가입 상태: " + userSignupResultDto.getMsg());
        return userSignupResultDto;
    }

    // 2. 로그인
    @PostMapping("/login")
    public UserLoginResultDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        LOGGER.info("로그인 시도: " + userLoginRequestDto.getEmail());
        UserLoginResultDto userLoginResultDto = userService.login(userLoginRequestDto);

        if(userLoginResultDto.getCode() == 0) {
            LOGGER.info("로그인 성공: " + userLoginResultDto.getToken());
        }
        return userLoginResultDto;
    }
}
