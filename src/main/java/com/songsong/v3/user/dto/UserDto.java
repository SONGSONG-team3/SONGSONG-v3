package com.songsong.v3.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private int userNo; // userNo
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userNickname;
    private String userImage;
    private Date userRegisterDate;
    private int userLike;

    private List<UserCategoryDto> userCategoryDtoList = new ArrayList<>();

}