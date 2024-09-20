package com.songsong.v3.playlist.controller;

import com.songsong.v3.like.entity.UserLike;
import com.songsong.v3.like.repository.LikeRepository;
import com.songsong.v3.like.service.LikeService;
import com.songsong.v3.music.entity.Music;
import com.songsong.v3.music.service.MusicService;
import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.service.PlaylistService;
import com.songsong.v3.playlist.dto.PlaylistParamDto;

import com.songsong.v3.playlist.dto.PlaylistResultDto;
import com.songsong.v3.user.repository.CategoryRepository;
import com.songsong.v3.playlist.service.PlaylistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.entity.Category;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.entity.UserCategory;
import com.songsong.v3.user.service.CategoryService;
import com.songsong.v3.user.service.UserCategoryService;
import com.songsong.v3.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/playlists/")

@Controller
@RequestMapping("/api/v3/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final CategoryRepository categoryRepository;
    private final UserCategoryService userCategoryService;
    private final UserService userService;
    private final MusicService musicService;
    private final CategoryService categoryService;
    private final LikeService likeService;

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
    @GetMapping("/myplaylist/{userNo}")
    public ResponseEntity<Map<String, Object>> getMyPlaylists(@PathVariable("userNo") int userNo) {
        List<Playlist> myplaylists = playlistService.findByUserUserNo(userNo);
        List<UserCategory> userCategories = userCategoryService.findByUserNo(userNo);
        List<Category> categories = userCategories.stream()
                .map(UserCategory::getCategory)
                .collect(Collectors.toList());

        User user = userService.findByUserNo(userNo);

        Map<String, Object> response = new HashMap<>();
        response.put("playlists", myplaylists);
        response.put("categories", categories);
        response.put("userNo", userNo);
        response.put("user", user);
        response.put("isPlaylistEmpty", myplaylists.isEmpty());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/deleteMusicFromPlaylist")
    public ResponseEntity<String> deleteMusicFromPlaylist(@RequestParam("userNo") int userNo, @RequestParam("musicId") int musicId) {
        playlistService.deleteMusicFromPlaylist(userNo, musicId);
        return ResponseEntity.ok("음악이 삭제되었습니다.");
    }

    @PostMapping("/addMusic")
    public ResponseEntity<String> addMusicToPlaylist(
            @RequestParam("songTitle") String songTitle,
            @RequestParam("artist") String artist,
            @RequestParam("songLink") String songLink,
            @RequestParam("category") int categoryId,
            @RequestParam("userNo") int userNo
    ) {
        Music music = new Music();
        music.setMusicName(songTitle);
        music.setMusicArtist(artist);
        music.setMusicLink(songLink);

        Category category = categoryService.findById(categoryId);
        music.setCategory(category);
        musicService.addMusic(music);

        Playlist playlist = new Playlist();
        playlist.setUser(userService.findByUserNo(userNo));
        playlist.setMusic(music);
        playlistService.save(playlist);

        return ResponseEntity.ok("음악이 추가되었습니다.");
    }

    @GetMapping("/otherplaylist/{userNo}")
    public ResponseEntity<Map<String, Object>> getOtherPlaylists(@PathVariable("userNo") int userNo) {
        List<Playlist> otherplaylists = playlistService.findByUserUserNo(userNo);
        List<UserCategory> userCategories = userCategoryService.findByUserNo(userNo);
        List<Category> categories = userCategories.stream()
                .map(UserCategory::getCategory)
                .collect(Collectors.toList());

        User user = userService.findByUserNo(userNo);

        Map<String, Object> response = new HashMap<>();
        response.put("playlists", otherplaylists);
        response.put("categories", categories);
        response.put("userNo", userNo);
        response.put("user", user);
        response.put("isPlaylistEmpty", otherplaylists.isEmpty());

        User fromUser = userService.findByUserNo(1); // 로그인한 사용자(테스트용)
        boolean isLiked = likeService.isLikedByUser(fromUser, user);
        response.put("isLiked", isLiked);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/toggleLike")
    public ResponseEntity<String> toggleLike(@RequestParam int userNo) {
        User fromUser = userService.findByUserNo(1); // 로그인한 사용자(테스트용)
        User toUser = userService.findByUserNo(userNo);

        boolean isLiked = likeService.isLikedByUser(fromUser, toUser);

        if (isLiked) {
            likeService.deleteByUserFromAndUserTo(fromUser, toUser);
            return ResponseEntity.ok("좋아요가 취소되었습니다.");
        } else {
            likeService.save(fromUser, toUser);
            return ResponseEntity.ok("좋아요가 추가되었습니다.");
        }
    }

    @PostMapping("/addMusicToPlaylist")
    public ResponseEntity<String> addMusicToPlaylist(@RequestParam int userNo, @RequestParam int musicId) {
        User loginUser = userService.findByUserNo(1); // 임시 로그인 유저

        List<Playlist> existingPlaylists = playlistService.findByUserAndMusic(loginUser.getUserNo(), musicId);
        if (!existingPlaylists.isEmpty()) {
            return ResponseEntity.badRequest().body("이미 플레이리스트에 추가된 음악입니다.");
        }

        playlistService.addMusicToUserPlaylist(loginUser.getUserNo(), musicId);
        return ResponseEntity.ok("음악이 성공적으로 추가되었습니다.");
    }
}