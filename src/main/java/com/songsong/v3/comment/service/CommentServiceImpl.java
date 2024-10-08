package com.songsong.v3.comment.service;

import com.songsong.v3.comment.dto.CommentDto;
import com.songsong.v3.comment.dto.CommentResponseDto;
import com.songsong.v3.comment.entity.Comment;
import com.songsong.v3.comment.repository.CommentRepository;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.repository.PlaylistRepository;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public ResponseEntity<CommentResponseDto> createComment(CommentDto commentDto) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(commentDto.getPlaylistId());
        Optional<User> optionalUser = userRepository.findById(commentDto.getUserId());
        Comment comment = Comment.builder()
                .playlist(optionalPlaylist.get())
                .user(optionalUser.get())
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);

        return ResponseEntity.ok(commentResponseDto);
    }
}