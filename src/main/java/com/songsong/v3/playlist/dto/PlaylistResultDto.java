package com.songsong.v3.playlist.dto;
import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.dto.UserDto;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaylistResultDto {
    private String result;
    private List<PlaylistDto> list;
    private PlaylistDto dto;
    private int count;
    private Map<Integer, UserDto> userMap;
    private Map<Integer, Integer> songCountMap;
    private Map<Integer, List<CategoryDto>> userCategoryMap;
    private int totalCount;
    private int totalPages;

}
