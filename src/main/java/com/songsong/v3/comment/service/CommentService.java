package com.songsong.v3.comment.service;

import com.songsong.v3.comment.dto.CommentDto;
import com.songsong.v3.comment.dto.CommentResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<CommentResponseDto> createComment(CommentDto commentDto);
}
