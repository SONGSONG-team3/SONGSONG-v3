package com.songsong.v3.music.dto;

import com.songsong.v3.user.dto.CategoryDto;
import lombok.Data;

@Data
public class MusicDto {
    private int musicId;
    private String musicName;
    private String musicArtist;
    private CategoryDto categoryDto;
    private String musicLink;
    private int categoryId;
}