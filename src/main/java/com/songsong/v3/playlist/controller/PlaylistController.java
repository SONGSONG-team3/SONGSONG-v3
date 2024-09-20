package com.songsong.v3.playlist.controller;
import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.service.PlaylistService;
import com.songsong.v3.playlist.dto.PlaylistParamDto;
import com.songsong.v3.playlist.dto.PlaylistResultDto;
import com.songsong.v3.user.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/playlists/")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/mainplaylists/{categoryId}")
    public ResponseEntity<PlaylistResultDto> getPlaylistsByCategory(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {

        // 카테고리 이름 가져오기 (JPA로 변환)
        String categoryName = categoryRepository.findById(categoryId)
                .map(category -> category.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        PlaylistParamDto playlistParamDto = new PlaylistParamDto();
        playlistParamDto.setSearchCategory(categoryId);
        playlistParamDto.setLimit(size);
        playlistParamDto.setOffset(page * size);

        PlaylistResultDto resultDto = playlistService.getPlaylistsByCategory(playlistParamDto);

        return ResponseEntity.ok(resultDto);
    }
}