package com.songsong.v3.playlist.service;

import com.songsong.v3.playlist.dto.PlaylistParamDto;
import com.songsong.v3.playlist.dto.PlaylistResultDto;
import com.songsong.v3.playlist.entity.Playlist;

import java.util.List;

public interface PlaylistService {
    PlaylistResultDto getPlaylistsByCategory(PlaylistParamDto playlistParamDto);

    // 좋아요 한 플레이리스트들 가져오기
    PlaylistResultDto getLikedPlaylistByUser(int userNo);

    List<Playlist> findByUserUserNo(int userNo);
    void deleteMusicFromPlaylist(int userNo, int musicId);
    void save(Playlist playlist);
    void addMusicToUserPlaylist(int userNo, int musicId);
    List<Playlist> findByUserAndMusic(int userNo, int musicId);
}