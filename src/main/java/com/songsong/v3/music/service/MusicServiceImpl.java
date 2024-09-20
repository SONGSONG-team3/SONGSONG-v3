package com.songsong.v3.music.service;

import com.songsong.v3.music.entity.Music;
import com.songsong.v3.music.repository.MusicRepository;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

@Service
public class MusicServiceImpl implements MusicService{

    private final MusicRepository musicRepository;
    private final PlaylistRepository playlistRepository;

    public MusicServiceImpl(MusicRepository musicRepository, PlaylistRepository playlistRepository) {
        this.musicRepository = musicRepository;
        this.playlistRepository = playlistRepository;
    }
    @Override
    public void addMusic(Music music) {
        musicRepository.save(music);
    }

    @Override
    public Music findById(int musicId) {
        return musicRepository.findMusicByMusicId(musicId);
    }
}
