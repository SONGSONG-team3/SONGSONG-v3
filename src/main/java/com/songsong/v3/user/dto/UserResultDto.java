package com.songsong.v3.user.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResultDto {
    private String result;
    private MypageDto mypageDto;
    private UserDto userDto;
    private List<UserCategoryDto> userCategoryDtoList;

}
