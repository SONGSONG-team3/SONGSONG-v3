package com.songsong.v3.playlist.dto;

import com.songsong.v3.music.dto.MusicDto;
import com.songsong.v3.user.dto.UserDto;
import lombok.Data;

@Data
public class PlaylistDto {
    private int playlistId;
    private UserDto user;
    private MusicDto music;
}
