package com.songsong.v3.user.dto;

import com.songsong.v3.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private int userNo;
    private String userName;
    private String userNickname;
    private String userPassword;
    private String userEmail;
    private String userImage;
    private Date userRegisterDate;
    private int userLike;

    private List<UserCategoryDto> userCategoryDtoList = new ArrayList<>();

    public UserDto(int userNo, String userName, String userNickname, String userEmail, String userImage, int userLike) {
        this.userNo = userNo;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userImage = userImage;
        this.userLike = userLike;
    }

    public UserDto(User user) {
        this.userNo = user.getUserNo();
        this.userName = user.getUserName();
        this.userNickname = user.getUserNickname();
        this.userEmail = user.getUserEmail();
        this.userImage = user.getUserImage();
        this.userLike = user.getUserLike();
    }
}
