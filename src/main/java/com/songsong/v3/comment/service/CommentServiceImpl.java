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
import org.springframework.http.HttpStatus;
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

        if (!optionalUser.isPresent()) {
            commentResponseDto.setStatus("fail");
            commentResponseDto.setMessage("사용자를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commentResponseDto);
        }

        try{
            Comment comment = Comment.builder()
                    .playlist(optionalPlaylist.get())
                    .user(optionalUser.get())
                    .content(commentDto.getContent())
                    .createdAt(LocalDateTime.now())
                    .build();
            commentRepository.save(comment);
            commentResponseDto.setStatus("success");
            commentResponseDto.setMessage("댓글 생성을 성공했습니다.");
            return ResponseEntity.ok(commentResponseDto);

        } catch(Exception e) {
            commentResponseDto.setStatus("error");
            commentResponseDto.setMessage("서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commentResponseDto);
        }
    }
}