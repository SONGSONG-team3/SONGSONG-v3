package com.songsong.v3.playlist.controller;

import com.songsong.v3.playlist.dto.PlaylistResultDto;
import com.songsong.v3.playlist.service.PlaylistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/v3/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/liked")
    public ResponseEntity<PlaylistResultDto> getLikedPlaylists(HttpSession session) {
        // 세션에서 userNo를 가져옵니다.
//        Integer userNo = (Integer) session.getAttribute("userId");

        int userNo = 1;
        // userNo가 null인 경우 처리
//        if (userNo == null) {
//            return ResponseEntity.badRequest().body(null);
//        }

        // 서비스 메서드 호출
        PlaylistResultDto playlistResultDto = playlistService.getLikedPlaylistByUser(userNo);

        // 결과 반환
        return ResponseEntity.ok(playlistResultDto);
    }


}
