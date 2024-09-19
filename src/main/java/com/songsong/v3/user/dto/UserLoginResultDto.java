package com.songsong.v3.user.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginResultDto extends UserSignupResultDto{

    private String token;

    @Builder
    public UserLoginResultDto(boolean success, int code, String msg, String token) {
        super(success, code, msg);
        this.token = token;
    }

}