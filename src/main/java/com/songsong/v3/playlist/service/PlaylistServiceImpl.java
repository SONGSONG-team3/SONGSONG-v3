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

        // Pageable 설정 (0-based index)
        Pageable pageable = PageRequest.of(playlistParamDto.getOffset() / playlistParamDto.getLimit(), playlistParamDto.getLimit());

        // 페이지네이션이 적용된 Playlist 조회
        //Page<Playlist> playlistPage = playlistRepository.findByMusic_Category_CategoryId(playlistParamDto.getSearchCategory(), pageable);

        // categoryId로 userNo 목록을 가져옴
        List<Integer> userNos = usercategoryRepository.findUserNoByCategoryId(playlistParamDto.getSearchCategory());

        System.out.println("카테고리 ID " + playlistParamDto.getSearchCategory() + "에 해당하는 유저 No: " + userNos);

        // userNo 목록으로 Playlist를 페이지 형태로 가져옴
        Page<Playlist> playlistPage = playlistRepository.findDistinctPlaylistsByUserNos(userNos, pageable);


        System.out.println("전체 요소 수: " + playlistPage.getTotalElements());
        System.out.println("전체 페이지 수: " + playlistPage.getTotalPages());
        System.out.println("현재 페이지의 요소 수: " + playlistPage.getNumberOfElements());
        System.out.println("현재 페이지 번호: " + playlistPage.getNumber());
        System.out.println("페이지 크기: " + playlistPage.getSize());

// 각 Playlist 객체 정보 출력
        for (Playlist playlist : playlistPage.getContent()) {
            System.out.println("플레이리스트 ID: " + playlist.getPlaylistId());
            System.out.println("사용자 ID: " + playlist.getUser().getUserNo());
            System.out.println("음악 ID: " + playlist.getMusic().getMusicId());
        }


        // 전체 플레이리스트 개수
        int totalPlaylistsCount = userNos.size();
        int totalPages = (int) Math.ceil((double) totalPlaylistsCount / playlistParamDto.getLimit());

        System.out.println(totalPlaylistsCount);
        System.out.println(totalPages);

        // PlaylistDto 변환
        Set<Integer> processedUserNos = new HashSet<>();  // 처리된 사용자 ID를 저장할 Set

        for (Playlist playlist : playlistPage.getContent()) {
            int userNo = playlist.getUser().getUserNo();

            // 이미 처리된 사용자라면 건너뜀
            if (processedUserNos.contains(userNo)) {
                continue;
            }

            // 사용자 ID를 Set에 추가하여 중복 처리 방지
            processedUserNos.add(userNo);

            // PlaylistDto로 변환하여 리스트에 추가
            PlaylistDto dto = new PlaylistDto();
            dto.setUserNo(userNo);
            dto.setMusicId(playlist.getMusic().getMusicId());
            dto.setSameUser(userNo == playlistParamDto.getUserNo());
            playlistDtoList.add(dto);
        }

        resultDto.setList(playlistDtoList);

        // 유저 정보와 곡 수 계산
        Map<Integer, UserDto> userMap = new HashMap<>();
        Map<Integer, Integer> songCountMap = new HashMap<>();
        Map<Integer, List<CategoryDto>> userCategoryMap = new HashMap<>();

        for (Playlist playlist : playlistPage.getContent()) {
            UserDto userDto = userRepository.findById(playlist.getUser().getUserNo())
                    .map(user -> new UserDto(user))
                    .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
            int songCount = playlistRepository.countByUser_UserNo(playlist.getUser().getUserNo());

            userMap.put(playlist.getUser().getUserNo(), userDto);
            songCountMap.put(playlist.getUser().getUserNo(), songCount);

            List<CategoryDto> categoryDtoList = categoryRepository.findCategoriesByUserNo(playlist.getUser().getUserNo());
            userCategoryMap.put(playlist.getUser().getUserNo(), categoryDtoList);
        }

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
