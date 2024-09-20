package com.songsong.v3.user.controller;

import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.dto.UserResultDto;
import com.songsong.v3.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v3/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

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
