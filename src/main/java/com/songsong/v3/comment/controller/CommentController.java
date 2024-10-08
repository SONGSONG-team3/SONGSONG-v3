package com.songsong.v3.comment.controller;


import com.songsong.v3.comment.dto.CommentDto;
import com.songsong.v3.comment.dto.CommentResponseDto;
import com.songsong.v3.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v3/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentDto commentDto) {
        return commentService.createComment(commentDto);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPlaylistId(@PathVariable int playlistId) {
        List<CommentDto> comments = commentService.getCommentsByPlaylistId(playlistId);
        return ResponseEntity.ok(comments);
    }
}
