package com.songsong.v3.comment.repository;

import com.songsong.v3.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPlaylistPlaylistId(int playlistId); // Playlist ID를 기준으로 댓글 조회

}
