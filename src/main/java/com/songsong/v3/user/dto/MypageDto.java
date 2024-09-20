package com.songsong.v3.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MypageDto {

    private UserDto userDto;
    private List<Integer> categoryIds;

}
