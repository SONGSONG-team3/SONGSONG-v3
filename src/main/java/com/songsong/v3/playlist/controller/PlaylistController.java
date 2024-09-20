package com.songsong.v3.playlist.controller;

import com.songsong.v3.like.entity.UserLike;
import com.songsong.v3.like.repository.LikeRepository;
import com.songsong.v3.like.service.LikeService;
import com.songsong.v3.music.entity.Music;
import com.songsong.v3.music.service.MusicService;
import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.service.PlaylistService;
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

@Controller
@RequestMapping("/api/v3/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserCategoryService userCategoryService;
    private final UserService userService;
    private final MusicService musicService;
    private final CategoryService categoryService;
    private final LikeService likeService;

    public PlaylistController(PlaylistService playlistService, UserCategoryService userCategoryService, UserService userService, MusicService musicService, CategoryService categoryService, LikeService likeService) {
        this.playlistService = playlistService;
        this.userCategoryService = userCategoryService;
        this.userService = userService;
        this.musicService = musicService;
        this.categoryService = categoryService;
        this.likeService = likeService;
    }

    @GetMapping("/myplaylist/{userNo}")
    public String getMyPlaylists(@PathVariable("userNo") int userNo, Model model) {
        List<Playlist> myplaylists = playlistService.findByUserUserNo(userNo);
        List<UserCategory> userCategories = userCategoryService.findByUserNo(userNo);
        List<Category> categories = userCategories.stream()
                .map(UserCategory::getCategory)
                .collect(Collectors.toList());

        User user = userService.findByUserNo(userNo);

        model.addAttribute("playlists", myplaylists);
        model.addAttribute("categories", categories);
        model.addAttribute("userNo", userNo);
        model.addAttribute("user", user);

        model.addAttribute("isPlaylistEmpty", myplaylists.isEmpty());
        return "myplaylist";
    }

    @PostMapping("/deleteMusicFromPlaylist")
    public String deleteMusicFromPlaylist(@RequestParam("userNo") int userNo, @RequestParam("musicId") int musicId) {
        playlistService.deleteMusicFromPlaylist(userNo, musicId);
        return "redirect:/api/v3/playlist/myplaylist/" + userNo;
    }

    @PostMapping("/addMusic")
    public String addMusicToPlaylist(
            @RequestParam("songTitle") String songTitle,
            @RequestParam("artist") String artist,
            @RequestParam("songLink") String songLink,
            @RequestParam("category") int categoryId,
            @RequestParam("userNo") int userNo,
            RedirectAttributes redirectAttributes
    ) {
        // Music 엔티티 생성 및 저장
        Music music = new Music();
        music.setMusicName(songTitle);
        music.setMusicArtist(artist);
        music.setMusicLink(songLink);

        // 카테고리 처리
        Category category = categoryService.findById(categoryId);
        music.setCategory(category);

        // Music 추가
        musicService.addMusic(music);

        // Playlist에 음악 추가
        Playlist playlist = new Playlist();
        playlist.setUser(userService.findByUserNo(userNo));
        playlist.setMusic(music);

        playlistService.save(playlist);

        return "redirect:/api/v3/playlist/myplaylist/" + userNo;
    }

    @GetMapping("/otherplaylist/{userNo}")
    public String getOtherPlaylists(@PathVariable("userNo") int userNo, Model model) {

        //로그인한 유저와 같은 유저일때 리다이렉트 구현


        List<Playlist> otherplaylists = playlistService.findByUserUserNo(userNo);
        List<UserCategory> userCategories = userCategoryService.findByUserNo(userNo);
        List<Category> categories = userCategories.stream()
                .map(UserCategory::getCategory)
                .collect(Collectors.toList());

        User user = userService.findByUserNo(userNo);

        model.addAttribute("playlists", otherplaylists);
        model.addAttribute("categories", categories);
        model.addAttribute("userNo", userNo);
        model.addAttribute("user", user);
        model.addAttribute("isPlaylistEmpty", otherplaylists.isEmpty());

        // 좋아요 버튼 상태 - 로그인 구현 후 처리 예정
        User fromUser = userService.findByUserNo(1); //로그인한 사용자(테스트용)
        boolean isLiked = likeService.isLikedByUser(fromUser, user);
        model.addAttribute("isLiked", isLiked);

        return "otherplaylist";
    }

    @PostMapping("/toggleLike")
    public String toggleLike(@RequestParam int userNo, RedirectAttributes redirectAttributes) {
        User fromUser = userService.findByUserNo(1); // 로그인한 사용자(테스트용)
        User toUser = userService.findByUserNo(userNo); // 페이지 주인

        boolean isLiked = likeService.isLikedByUser(fromUser, toUser);

        if (isLiked) {
            likeService.deleteByUserFromAndUserTo(fromUser, toUser); // 좋아요 취소
            redirectAttributes.addFlashAttribute("message", "좋아요가 취소되었습니다.");
        } else {
            likeService.save(fromUser, toUser); // 좋아요 추가
            redirectAttributes.addFlashAttribute("message", "좋아요가 추가되었습니다.");
        }

        return "redirect:/api/v3/playlist/otherplaylist/" + userNo; // 리다이렉트
    }

    @PostMapping("/addMusicToPlaylist")
    public String addMusicToPlaylist(@RequestParam int userNo) {


        return "redirect:/api/v3/playlist/otherplaylist/" + userNo;
    }
}
