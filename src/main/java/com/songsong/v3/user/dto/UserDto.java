package com.songsong.v3.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private int userNo;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userNickname;
    private String userImage;
    private LocalDateTime userRegisterDate;
    private int userLike;

}