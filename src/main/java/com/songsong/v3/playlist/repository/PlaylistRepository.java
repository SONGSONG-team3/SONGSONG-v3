package com.songsong.v3.playlist.repository;

import com.songsong.v3.playlist.entity.Playlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    List<Playlist> findByUser_UserNo(int userNo);
    @Modifying
    @Transactional
    @Query("DELETE FROM Playlist p WHERE p.user.userNo = :userNo AND p.music.musicId = :musicId")
    void deleteByUserNoAndMusicId(@Param("userNo") int userNo, @Param("musicId") int musicId);
}