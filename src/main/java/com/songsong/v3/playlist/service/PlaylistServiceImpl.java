package com.songsong.v3.playlist.service;

import com.songsong.v3.music.entity.Music;
import com.songsong.v3.music.repository.MusicRepository;
import com.songsong.v3.music.service.MusicService;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.repository.PlaylistRepository;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.repository.UserRepository;
import com.songsong.v3.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService{

    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;
    private final MusicService musicService;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, MusicRepository musicRepository, UserRepository userRepository, MusicService musicService) {
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
        this.userRepository = userRepository;
        this.musicService = musicService;
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
    public void addMusicToUserPlaylist(int userNo, int musicId) {
        Playlist newPlaylist = new Playlist();
        User user = userRepository.findByUserNo(userNo);
        Music music = musicService.findById(musicId);
        newPlaylist.setUser(user);
        newPlaylist.setMusic(music);

        playlistRepository.save(newPlaylist);
    }

    @Override
    public List<Playlist> findByUserAndMusic(int userNo, int musicId) {
        return playlistRepository.findByUserUserNoAndMusicMusicId(userNo, musicId);
    }
}
