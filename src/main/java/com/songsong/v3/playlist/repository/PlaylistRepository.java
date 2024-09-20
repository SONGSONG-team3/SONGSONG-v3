package com.songsong.v3.playlist.repository;

import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

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


}
