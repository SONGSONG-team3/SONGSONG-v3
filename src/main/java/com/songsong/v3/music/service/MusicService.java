package com.songsong.v3.music.service;

import com.songsong.v3.music.entity.Music;

public interface MusicService {
    void addMusic(Music music);
    Music findById(int musicId);
}
