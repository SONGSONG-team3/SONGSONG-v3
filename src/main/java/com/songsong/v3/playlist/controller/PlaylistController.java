package com.songsong.v3.playlist.controller;


import com.songsong.v3.common.JwtTokenProvider;
import com.songsong.v3.like.service.LikeService;
import com.songsong.v3.music.dto.MusicDto;
import com.songsong.v3.music.entity.Music;
import com.songsong.v3.music.service.MusicService;
import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.service.PlaylistService;
import com.songsong.v3.playlist.dto.PlaylistParamDto;
import com.songsong.v3.playlist.dto.PlaylistResultDto;
import com.songsong.v3.user.controller.UserApiController;
import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.repository.CategoryRepository;
import com.songsong.v3.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.songsong.v3.user.entity.Category;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.entity.UserCategory;
import com.songsong.v3.user.service.CategoryService;
import com.songsong.v3.user.service.UserCategoryService;
import com.songsong.v3.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/playlists/")
public class PlaylistController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);
    private final JwtTokenProvider jwtTokenProvider;
    private final PlaylistService playlistService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final UserCategoryService userCategoryService;
    private final UserService userService;
    private final MusicService musicService;
    private final CategoryService categoryService;
    private final LikeService likeService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<PlaylistResultDto> getPlaylistsByCategory(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {

        // 카테고리 이름 가져오기 (JPA로 변환)
//        String categoryName = categoryRepository.findById(categoryId)
//                .map(category -> category.getCategoryName())
//                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        PlaylistParamDto playlistParamDto = new PlaylistParamDto();
        playlistParamDto.setSearchCategory(categoryId);
        playlistParamDto.setLimit(size);
        playlistParamDto.setOffset(page * size);

        PlaylistResultDto resultDto = playlistService.getPlaylistsByCategory(playlistParamDto);

        return ResponseEntity.ok(resultDto);
    }

    @GetMapping("/liked")
    public ResponseEntity<PlaylistResultDto> getLikedPlaylists(@RequestHeader("Authorization") String token) {

        LOGGER.info("사용자 정보 요청: 토큰 검증 시작");

        String jwtToken = token.substring(7);

        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);

        User user = userRepository.findByUserEmail(userEmail);
        int userNo = user.getUserNo();

        PlaylistResultDto playlistResultDto = playlistService.getLikedPlaylistByUser(userNo);
        return ResponseEntity.ok(playlistResultDto);
    }

    // 내 플레이리스트 조회
    @GetMapping("/myplaylist")
    public ResponseEntity<Map<String, Object>> getMyPlaylists(@RequestHeader("Authorization") String token) {
        LOGGER.info("사용자 플레이리스트 요청: 토큰 검증 시작");

        String jwtToken = token.substring(7);
        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);

        User user = userRepository.findByUserEmail(userEmail);
        UserDto userDto = new UserDto();
        userDto.setUserNo(user.getUserNo());
        userDto.setUserName(user.getUserName());
        userDto.setUserNickname(user.getUserNickname());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserImage(String.valueOf(user.getUserImage()));
        userDto.setUserLike(user.getUserLike());

        int userNo = user.getUserNo();

        List<Playlist> myplaylists = playlistService.findByUserUserNo(userNo);
        List<PlaylistDto> playlistDtos = myplaylists.stream()
                .map(playlist -> {
                    Music music = playlist.getMusic(); // Music 정보를 가져옴
                    MusicDto musicDto = new MusicDto();
                    if (music != null) {
                        musicDto.setMusicId(music.getMusicId());
                        musicDto.setMusicName(music.getMusicName());
                        musicDto.setMusicArtist(music.getMusicArtist());
                        musicDto.setMusicLink(music.getMusicLink());
                        musicDto.setCategoryDto(new CategoryDto(music.getCategory().getCategoryId(), music.getCategory().getCategoryName()));
                    }

                    PlaylistDto dto = new PlaylistDto();
                    dto.setUserNo(userNo);
                    dto.setMusic(musicDto); // MusicDto 설정
                    return dto;
                }).collect(Collectors.toList());

        List<UserCategory> userCategories = userCategoryService.findByUserNo(userNo);
        List<CategoryDto> categoryDtos = userCategories.stream()
                .map(userCategory -> {
                    Category category = userCategory.getCategory();
                    return new CategoryDto(category.getCategoryId(), category.getCategoryName()); // DTO 변환
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("playlists", playlistDtos);
        response.put("categories", categoryDtos);
        response.put("user", userDto);
        response.put("userNo", userNo);
        response.put("isPlaylistEmpty", myplaylists.isEmpty());

        LOGGER.info("반환 데이터: " + response.toString());

        return ResponseEntity.ok(response);
    }

    // 내 플레이리스트 음악 삭제
    @DeleteMapping("/{musicId}") // musicId를 경로에서 가져옴
    public ResponseEntity<String> deleteMusicFromPlaylist(
            @PathVariable int musicId,
            @RequestHeader("Authorization") String token) {
        LOGGER.info("사용자 플레이리스트 음악 삭제 요청: 토큰 검증 시작");
        String jwtToken = token.substring(7);
        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);
        User user = userRepository.findByUserEmail(userEmail);
        int userNo = user.getUserNo();
        playlistService.deleteMusicFromPlaylist(userNo, musicId);
        return ResponseEntity.ok("음악이 삭제되었습니다.");
    }

    // 내 플레이리스트 음악 추가
    @PostMapping("/my")
    public ResponseEntity<String> addMusicToPlaylist(
            @RequestBody MusicDto musicDto,
            @RequestHeader("Authorization") String token) {
        LOGGER.info("사용자 플레이리스트 음악 추가 요청: 토큰 검증 시작");
        LOGGER.info("musicDto: " + musicDto);
        String jwtToken = token.substring(7); // "Bearer " 제거
        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);
        User user = userRepository.findByUserEmail(userEmail);
        Music music = new Music();
        music.setMusicName(musicDto.getMusicName());
        music.setMusicArtist(musicDto.getMusicArtist());
        music.setMusicLink(musicDto.getMusicLink());

        Category category = categoryService.findById(musicDto.getCategoryId());
        System.out.println(musicDto.getCategoryId());
        music.setCategory(category);
        musicService.addMusic(music);

        Playlist playlist = new Playlist();
        playlist.setUser(user);
        playlist.setMusic(music);
        playlistService.save(playlist);

        return ResponseEntity.ok("음악이 추가되었습니다.");
    }

    // 다른 사용자 플레이리스트 조회
    @GetMapping("/otherplaylist/{userNo}")
    public ResponseEntity<Map<String, Object>> getOtherPlaylists(@PathVariable("userNo") int userNo, @RequestHeader("Authorization") String token) {
        LOGGER.info("다른 사용자 플레이리스트 조회 요청: 토큰 검증 시작");
        String jwtToken = token.substring(7);
        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);
        User loginuser = userRepository.findByUserEmail(userEmail);//로그인한 사용자
        String loginuser_name = loginuser.getUserName();

        User user = userService.findByUserNo(userNo); //userNo: 다른 사용자의 userNo

        UserDto userDto = new UserDto();
        userDto.setUserNo(user.getUserNo());
        userDto.setUserName(user.getUserName());
        userDto.setUserNickname(user.getUserNickname());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserImage(String.valueOf(user.getUserImage()));
        userDto.setUserLike(user.getUserLike());

        List<Playlist> otherplaylists = playlistService.findByUserUserNo(userNo);
        List<PlaylistDto> playlistDtos = otherplaylists.stream()
                .map(playlist -> {
                    Music music = playlist.getMusic(); // Music 정보를 가져옴
                    MusicDto musicDto = new MusicDto();
                    if (music != null) {
                        musicDto.setMusicId(music.getMusicId());
                        musicDto.setMusicName(music.getMusicName());
                        musicDto.setMusicArtist(music.getMusicArtist());
                        musicDto.setMusicLink(music.getMusicLink());
                        musicDto.setCategoryDto(new CategoryDto(music.getCategory().getCategoryId(), music.getCategory().getCategoryName()));
                    }

                    PlaylistDto dto = new PlaylistDto();
                    dto.setUserNo(userNo);
                    dto.setMusic(musicDto); // MusicDto 설정
                    return dto;
                }).collect(Collectors.toList());

        List<UserCategory> otherCategories = userCategoryService.findByUserNo(userNo);
        List<CategoryDto> categoryDtos = otherCategories.stream()
                .map(userCategory -> {
                    Category category = userCategory.getCategory();
                    return new CategoryDto(category.getCategoryId(), category.getCategoryName()); // DTO 변환
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("loginuser_name", loginuser_name);
        response.put("playlists", playlistDtos);
        response.put("categories", categoryDtos);
        response.put("user", userDto);
        response.put("isPlaylistEmpty", otherplaylists.isEmpty());

        boolean isLiked = likeService.isLikedByUser(loginuser, user);
        response.put("isLiked", isLiked);

        return ResponseEntity.ok(response);
    }

    // 플레이리스트 좋아요
    @PostMapping("/like")
    public ResponseEntity<Map<String, Object>> toggleLike(@RequestBody UserDto userDto, @RequestHeader("Authorization") String token) {
        LOGGER.info("사용자 좋아요 요청: 토큰 검증 시작");
        String jwtToken = token.substring(7); // "Bearer " 제거
        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);
        User fromUser = userRepository.findByUserEmail(userEmail);

        User toUser = userService.findByUserNo(userDto.getUserNo());

        boolean isLiked = likeService.isLikedByUser(fromUser, toUser);
        Map<String, Object> response = new HashMap<>();

        if (isLiked) {
            likeService.deleteByUserFromAndUserTo(fromUser, toUser);
            toUser.setUserLike(toUser.getUserLike() - 1);
            userService.save(toUser);
            response.put("message", "좋아요가 취소되었습니다.");
            response.put("isLiked", false);
        } else {
            likeService.save(fromUser, toUser);
            toUser.setUserLike(toUser.getUserLike() + 1);
            userService.save(toUser);
            response.put("message", "좋아요가 추가되었습니다.");
            response.put("isLiked", true);
        }

        return ResponseEntity.ok(response);
    }

    // 다른 사용자 플레이리스트에서 음악 추가
    @PostMapping("/other")
    public ResponseEntity<String> addOtherMusicToPlaylist(@RequestBody MusicDto musicDto, @RequestHeader("Authorization") String token) {
        LOGGER.info("다른 사용자 노래 추가 요청: 토큰 검증 시작");
        String jwtToken = token.substring(7); // "Bearer " 제거
        String userEmail = jwtTokenProvider.getUserEmail(jwtToken);
        User loginUser = userRepository.findByUserEmail(userEmail);

        List<Playlist> existingPlaylists = playlistService.findByUserAndMusic(loginUser.getUserNo(), musicDto.getMusicId());
        if (!existingPlaylists.isEmpty()) {
            return ResponseEntity.badRequest().body("이미 플레이리스트에 추가된 음악입니다.");
        }

        playlistService.addMusicToUserPlaylist(loginUser.getUserNo(), musicDto.getMusicId());
        return ResponseEntity.ok("음악이 성공적으로 추가되었습니다.");
    }
}