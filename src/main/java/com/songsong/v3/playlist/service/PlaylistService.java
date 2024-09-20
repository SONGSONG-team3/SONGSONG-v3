package com.songsong.v3.playlist.service;

import com.songsong.v3.playlist.dto.PlaylistResultDto;

public interface PlaylistService {

    // 좋아요 한 플레이리스트들 가져오기
    PlaylistResultDto getLikedPlaylistByUser(int userNo);
}
