package com.songsong.v3.comment.controller;


import com.songsong.v3.comment.dto.CommentDto;
import com.songsong.v3.comment.dto.CommentResponseDto;
import com.songsong.v3.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentDto commentDto) {
        return commentService.createComment(commentDto);
    }
}
