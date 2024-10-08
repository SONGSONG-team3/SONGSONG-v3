package com.songsong.v3.comment.dto;

import lombok.Data;

@Data
public class CommentDto {
    private String content;
    private int playlistId;
    private int userId;
}