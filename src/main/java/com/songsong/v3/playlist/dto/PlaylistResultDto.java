package com.songsong.v3.playlist.dto;

import com.songsong.v3.like.dto.UserLikeDto;
import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.dto.UserDto;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PlaylistResultDto {
    private String result;

    private List<PlaylistDto> list;

    private PlaylistDto dto;
    private int count;

    private int likedPlaylistCount;

    // 유저 정보를 저장하는 MAP (userNo -> UserDto)
    private Map<Integer, UserDto> userMap;
    private Map<Integer, Integer> songCountMap;
    private Map<Integer, List<CategoryDto>> userCategoryMap;
    private int totalCount;
    private int totalPages;

}
