package com.songsong.v3.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSignupRequestDto {

    private UserDto userDto;
    private List<Integer> categories;

}
