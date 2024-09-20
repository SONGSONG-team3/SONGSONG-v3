package com.songsong.v3.playlist.dto;

import com.songsong.v3.like.dto.UserLikeDto;
import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PlaylistResultDto {
    // 요청 결과
    private String result;

    // 목록
    private List<PlaylistDto> list;

    // 상세
    private PlaylistDto dto;

    // 곡 전체 수
    private int count;

    // 좋아요 누른 전체 플레이리스트 수
    private int likedPlaylistCount;

    // 유저 정보를 저장하는 MAP (userNo -> UserDto)
    private Map<Integer, UserDto> userMap;
    private Map<Integer, Integer> songCountMap;
    private Map<Integer, List<CategoryDto>> userCategoryMap;

}
