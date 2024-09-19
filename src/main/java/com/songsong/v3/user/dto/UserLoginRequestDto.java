package com.songsong.v3.user.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserLoginRequestDto {
    private String email;
    private String password;
}
