package com.songsong.v3.playlist.service;

import com.songsong.v3.music.entity.Music;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.user.entity.User;

import java.util.List;

public interface PlaylistService {
    List<Playlist> findByUserUserNo(int userNo);
    void deleteMusicFromPlaylist(int userNo, int musicId);
    void save(Playlist playlist);
    void addMusicToUserPlaylist(User user, Music music);
}