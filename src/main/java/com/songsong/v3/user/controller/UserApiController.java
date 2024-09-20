package com.songsong.v3.user.controller;

import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.dto.UserResultDto;
import com.songsong.v3.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.songsong.v3.user.dto.UserLoginRequestDto;
import com.songsong.v3.user.dto.UserLoginResultDto;
import com.songsong.v3.user.dto.UserSignupRequestDto;
import com.songsong.v3.user.dto.UserSignupResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v3/users")
@RequiredArgsConstructor
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

        if (userLoginResultDto.getCode() == 0) {
            LOGGER.info("로그인 성공: " + userLoginResultDto.getToken());
        }
        return userLoginResultDto;
    }

    @GetMapping("/mypage")
    @ResponseBody
    public UserResultDto detailUser(HttpSession session) {
//        int userNo = (int) session.getAttribute("userNo"); // Assuming userNo is stored in session
        int userNo = 1;
        return userService.detailMypage(userNo);
    }

    @PostMapping("/mypage/update")
    @ResponseBody
    public UserResultDto updateUserMypage(@RequestBody UserDto userDto, HttpSession session) {
//        int userNo = (int) session.getAttribute("userNo"); // Assuming userNo is stored in session
        int userNo = 1;
        userDto.setUserNo(userNo);
        return userService.updateUserMypage(userDto);
    }

    @PostMapping("/mypage/updatecate")
    @ResponseBody
    public UserResultDto updateUserCategory(@RequestBody List<Integer> categoryIds, HttpSession session) {
//        int userNo = (int) session.getAttribute("userNo"); // Assuming userNo is stored in session
        int userNo = 1;
        return userService.updateUserCategory(userNo, categoryIds);
    }
}