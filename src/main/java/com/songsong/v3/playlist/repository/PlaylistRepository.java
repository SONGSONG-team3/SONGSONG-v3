package com.songsong.v3.playlist.repository;

import com.songsong.v3.playlist.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    Page<Playlist> findByMusic_Category_CategoryId(int categoryId, Pageable pageable);

    int countByMusic_Category_CategoryId(int categoryId);  // 카테고리별 곡 수를 세는 메소드

    int countByUser_UserNo(int userNo);  // 사용자별 곡 수를 세는 메소드

}
