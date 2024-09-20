package com.songsong.v3.music.repository;

import com.songsong.v3.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
    Music findMusicByMusicId(int musicId);
}
