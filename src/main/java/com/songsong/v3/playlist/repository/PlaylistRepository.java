package com.songsong.v3.playlist.repository;

import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    @Query("SELECT p FROM Playlist p WHERE p.user.userNo IN :userNos GROUP BY p.user.userNo, p.playlistId")
    Page<Playlist> findDistinctPlaylistsByUserNos(@Param("userNos") List<Integer> userNos, Pageable pageable);
    int countByMusic_Category_CategoryId(int categoryId);  // 카테고리별 곡 수를 세는 메소드
    int countByUser_UserNo(int userNo);  // 사용자별 곡 수를 세는 메소드

    // 마이페이지 - 좋아요 누른 플레이리스트들 조회 (중복 <- groupby, min으로 해결)
    @Query("SELECT new com.songsong.v3.playlist.dto.PlaylistDto(ul.userTo.userNo, MIN(m.music.musicId), false) " +
            "FROM UserLike ul JOIN ul.userTo u JOIN u.playlists m " +
            "WHERE ul.userFrom.userNo = :userNo " +
            "GROUP BY ul.userTo.userNo")
    List<PlaylistDto> selectLikedPlaylistByUser(@Param("userNo") int userNo);

    // 한 플리 안에 있는 곡 수
    @Query("select count(p) from Playlist p where p.user.userNo = :userNo")
    int getSongCountByUserNo(int userNo);

    // 좋아요 누른 전체 플레이리스트 수
    @Query("select count(ul) from UserLike ul where ul.userFrom.userNo = :userNo")
    int getPlaylistCountByLiked(int userNo);

    List<Playlist> findByUser_UserNo(int userNo);
    @Modifying
    @Transactional
    @Query("DELETE FROM Playlist p WHERE p.user.userNo = :userNo AND p.music.musicId = :musicId")
    void deleteByUserNoAndMusicId(@Param("userNo") int userNo, @Param("musicId") int musicId);
    List<Playlist> findByUserUserNoAndMusicMusicId(int userNo, int musicId);
}