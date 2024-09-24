package com.songsong.v3.playlist.service;

import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.dto.PlaylistParamDto;
import com.songsong.v3.playlist.dto.PlaylistResultDto;
import com.songsong.v3.music.entity.Music;
import com.songsong.v3.music.repository.MusicRepository;
import com.songsong.v3.music.service.MusicService;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.repository.PlaylistRepository;
import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.repository.UserRepository;
import com.songsong.v3.user.repository.CategoryRepository;
import com.songsong.v3.user.repository.UserCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository usercategoryRepository;
    private final MusicService musicService;

    @Override
    public PlaylistResultDto getPlaylistsByCategory(PlaylistParamDto playlistParamDto) {
        PlaylistResultDto resultDto = new PlaylistResultDto();
        List<PlaylistDto> playlistDtoList = new ArrayList<>();

        // categoryId로 userNo 목록을 가져옴
        List<Integer> userNos = usercategoryRepository.findUserNoByCategoryId(playlistParamDto.getSearchCategory());

        System.out.println("카테고리 ID " + playlistParamDto.getSearchCategory() + "에 해당하는 유저 No: " + userNos);

        // 전체 플레이리스트 개수 (실제 userNos의 크기를 기준으로 함)
        int totalPlaylistsCount = userNos.size();
        int totalPages = (int) Math.ceil((double) totalPlaylistsCount / playlistParamDto.getLimit());

        System.out.println(totalPlaylistsCount);
        System.out.println(totalPages);

        // 유저 정보와 곡 수 계산
        Map<Integer, UserDto> userMap = new HashMap<>();
        Map<Integer, Integer> songCountMap = new HashMap<>();
        Map<Integer, List<CategoryDto>> userCategoryMap = new HashMap<>();

        // Pageable 설정 (0-based index)
        int offset = playlistParamDto.getOffset();
        int limit = playlistParamDto.getLimit();
        List<Integer> pagedUserNos = userNos.subList(offset, Math.min(offset + limit, userNos.size()));  // 페이지 처리된 사용자 목록

        for (Integer userNo : pagedUserNos) {
            // 사용자 정보 가져오기
            UserDto userDto = userRepository.findById(userNo)
                    .map(user -> new UserDto(user))
                    .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
            userMap.put(userNo, userDto);

            // 사용자가 저장한 곡의 개수 계산
            int songCount = playlistRepository.countByUser_UserNo(userNo);
            songCountMap.put(userNo, songCount);

            // 사용자 카테고리 정보 가져오기
            List<CategoryDto> categoryDtoList = categoryRepository.findCategoriesByUserNo(userNo);
            userCategoryMap.put(userNo, categoryDtoList);

            // PlaylistDto 생성
            PlaylistDto dto = new PlaylistDto();
            dto.setUserNo(userNo);
            dto.setSameUser(userNo == playlistParamDto.getUserNo());
            playlistDtoList.add(dto);
        }

        resultDto.setList(playlistDtoList);
        resultDto.setResult("SUCCESS");
        resultDto.setTotalPages(totalPages);  // 총 페이지 수 설정
        resultDto.setUserMap(userMap);
        resultDto.setSongCountMap(songCountMap);
        resultDto.setUserCategoryMap(userCategoryMap);
        resultDto.setTotalCount(totalPlaylistsCount);

        return resultDto;
    }



    @Override
    public PlaylistResultDto getLikedPlaylistByUser(int userNo) {
        PlaylistResultDto playlistResultDto = new PlaylistResultDto();

        // 좋아요 누른 플레이리스트 목록 조회
        List<PlaylistDto> playlistDtoList = playlistRepository.selectLikedPlaylistByUser(userNo);
        playlistResultDto.setList(playlistDtoList);

        // 유저 정보를 Map에 저장
        Map<Integer, UserDto> userMap = new HashMap<>();
        Map<Integer, List<CategoryDto>> userCategoryMap = new HashMap<>();
        Map<Integer, Integer> songCountMap = new HashMap<>();

        // 좋아요 누른 플레이리스트의 총 개수
        int totalLikedCount = playlistRepository.getPlaylistCountByLiked(userNo);

        playlistDtoList.forEach(playlistDto -> {
            Optional<User> user = userRepository.findById(playlistDto.getUserNo());
            UserDto userDto = new UserDto();
            if (user.isPresent()) {
                userDto.setUserNo(user.get().getUserNo());
                userDto.setUserNickname(user.get().getUserNickname());
                userDto.setUserImage(user.get().getUserImage());
                userDto.setUserLike(user.get().getUserLike());

            } else {
                // 유저가 없는 경우 처리
                userDto = null; // 또는 기본값 설정
            }
            userMap.put(playlistDto.getUserNo(), userDto);

            int songCount = playlistRepository.getSongCountByUserNo(playlistDto.getUserNo());
            songCountMap.put(playlistDto.getUserNo(), songCount);

            List<CategoryDto> userCategories = userRepository.selectCategoriesByUserNo(playlistDto.getUserNo());
            userCategoryMap.put(playlistDto.getUserNo(), userCategories);
        });

        playlistResultDto.setResult("success");
        playlistResultDto.setUserMap(userMap);
        playlistResultDto.setSongCountMap(songCountMap);
        playlistResultDto.setUserCategoryMap(userCategoryMap);
        playlistResultDto.setLikedPlaylistCount(totalLikedCount);
        // 곡 전체 수 설정
        playlistResultDto.setCount(playlistDtoList.size()); // 또는 다른 방식으로 총 곡 수 계산

        return playlistResultDto;
    }
    @Override
    public List<Playlist> findByUserUserNo(int userNo) {
        return playlistRepository.findByUser_UserNo(userNo);
    }

    @Transactional
    public void deleteMusicFromPlaylist(int userNo, int musicId) {
        playlistRepository.deleteByUserNoAndMusicId(userNo, musicId);
    }

    @Override
    public void save(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public void addMusicToUserPlaylist(int userNo, int musicId) {
        Playlist newPlaylist = new Playlist();
        User user = userRepository.findByUserNo(userNo);
        Music music = musicService.findById(musicId);
        newPlaylist.setUser(user);
        newPlaylist.setMusic(music);

        playlistRepository.save(newPlaylist);
    }

    @Override
    public List<Playlist> findByUserAndMusic(int userNo, int musicId) {
        return playlistRepository.findByUserUserNoAndMusicMusicId(userNo, musicId);
    }
}
