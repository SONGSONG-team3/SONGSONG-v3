package com.songsong.v3.playlist.service;

import com.songsong.v3.playlist.dto.PlaylistParamDto;
import com.songsong.v3.playlist.dto.PlaylistResultDto;

public interface PlaylistService {
    PlaylistResultDto getPlaylistsByCategory(PlaylistParamDto playlistParamDto);
}
