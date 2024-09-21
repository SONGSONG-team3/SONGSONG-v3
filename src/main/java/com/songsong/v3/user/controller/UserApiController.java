package com.songsong.v3.user.controller;

import com.songsong.v3.common.JwtTokenProvider;
import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.dto.UserResultDto;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.repository.UserRepository;
import com.songsong.v3.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

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

    @GetMapping("/info")
    public UserDto getUserInfo(@RequestHeader("Authorization") String token) {
        LOGGER.info("사용자 정보 요청: 토큰 검증 시작");

        String jwtToken = token.substring(7);

        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);

        User user = userRepository.findByUserEmail(userEmail);
        int userNo = user.getUserNo();

        LOGGER.info("사용자 정보 요청: userNo = " + userEmail);

        // UserDTO로 변환해서 반환
        return new UserDto(
                user.getUserNo(),
                user.getUserName(),
                user.getUserNickname(),
                user.getUserEmail(),
                user.getUserImage(),
                user.getUserLike()
        );
    }


    @GetMapping("/mypage")
    @ResponseBody
    public UserResultDto detailUser(@RequestHeader("Authorization") String token) {

        LOGGER.info("사용자 정보 요청: 토큰 검증 시작");

        String jwtToken = token.substring(7);

        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);

        User user = userRepository.findByUserEmail(userEmail);
        int userNo = user.getUserNo();

        LOGGER.info("사용자 정보 요청: userNo = " + userEmail);


        return userService.detailMypage(userNo);
    }

    @PutMapping("/mypage/update")
    @ResponseBody
    public UserResultDto updateUserMypage(@RequestBody UserDto userDto, @RequestHeader("Authorization") String token ) {
        LOGGER.info("사용자 정보 요청: 토큰 검증 시작");

        String jwtToken = token.substring(7);
        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);

        User user = userRepository.findByUserEmail(userEmail);
        int userNo = user.getUserNo();
        userDto.setUserNo(userNo);

        LOGGER.info("사용자 정보 요청: userNo = " + userEmail);
        return userService.updateUserMypage(userDto);
    }

    @PutMapping("/mypage/updatecate")
    @ResponseBody
    public UserResultDto updateUserCategory(@RequestBody List<Integer> categoryIds, @RequestHeader("Authorization") String token) {
        LOGGER.info("사용자 정보 요청: 토큰 검증 시작");

        String jwtToken = token.substring(7);
        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);

        User user = userRepository.findByUserEmail(userEmail);
        int userNo = user.getUserNo();

        LOGGER.info("사용자 정보 요청: userNo = " + userEmail);
        return userService.updateUserCategory(userNo, categoryIds);
    }
}