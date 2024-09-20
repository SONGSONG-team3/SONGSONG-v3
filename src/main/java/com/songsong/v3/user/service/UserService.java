package com.songsong.v3.user.service;

import com.songsong.v3.user.dto.*;
import com.songsong.v3.user.dto.UserResultDto;

import java.util.List;

public interface UserService {

    UserResultDto detailMypage(int userNo); // 마이페이지 회원 정보 불러옴
    UserResultDto updateUserMypage(UserDto userDto); // 마이페이지 회원 정보 수정
    UserResultDto updateUserCategory(int userNo, List<Integer> categoryIds); // 카테고리 정보 수정


}
