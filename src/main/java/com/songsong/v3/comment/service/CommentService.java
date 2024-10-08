package com.songsong.v3.comment.service;

import com.songsong.v3.comment.dto.CommentDto;
import com.songsong.v3.comment.dto.CommentResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<CommentResponseDto> createComment(CommentDto commentDto);

    List<CommentDto> getCommentsByPlaylistId(int playlistId);  // playlist_id를 기준으로 댓글 조회

}
