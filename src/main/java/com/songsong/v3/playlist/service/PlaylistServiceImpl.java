package com.songsong.v3.playlist.service;

import com.songsong.v3.music.entity.Music;
import com.songsong.v3.music.repository.MusicRepository;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.repository.PlaylistRepository;
import com.songsong.v3.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService{

    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, MusicRepository musicRepository) {
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
    }

    @Override
    public List<Playlist> findByUserUserNo(int userNo) {
        return playlistRepository.findByUser_UserNo(userNo);
    }

    @Transactional
    public void deleteMusicFromPlaylist(int userNo, int musicId) {
        playlistRepository.deleteByUserNoAndMusicId(userNo, musicId);
    }

    @Override
    public void save(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public void addMusicToUserPlaylist(User user, Music music) {

    }
}
