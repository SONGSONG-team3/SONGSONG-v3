package com.songsong.v3.playlist.service;

import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.dto.PlaylistParamDto;
import com.songsong.v3.playlist.dto.PlaylistResultDto;
import com.songsong.v3.playlist.entity.Playlist;
import com.songsong.v3.playlist.repository.PlaylistRepository;
import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.repository.UserRepository;
import com.songsong.v3.user.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PlaylistResultDto getPlaylistsByCategory(PlaylistParamDto playlistParamDto) {
        PlaylistResultDto resultDto = new PlaylistResultDto();
        List<PlaylistDto> playlistDtoList = new ArrayList<>();

        // Pageable 설정 (0-based index)
        Pageable pageable = PageRequest.of(playlistParamDto.getOffset() / playlistParamDto.getLimit(), playlistParamDto.getLimit());

        // 페이지네이션이 적용된 Playlist 조회
        Page<Playlist> playlistPage = playlistRepository.findByMusic_Category_CategoryId(playlistParamDto.getSearchCategory(), pageable);

        // 전체 플레이리스트 개수
        int totalPlaylistsCount = (int) playlistPage.getTotalElements();
        int totalPages = playlistPage.getTotalPages();

        System.out.println(totalPlaylistsCount);
        System.out.println(totalPages);

        // PlaylistDto 변환
        for (Playlist playlist : playlistPage.getContent()) {
            PlaylistDto dto = new PlaylistDto();
            dto.setUserNo(playlist.getUser().getUserNo());
            dto.setMusicId(playlist.getMusic().getMusicId());
            dto.setSameUser(playlist.getUser().getUserNo() == playlistParamDto.getUserNo());
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
}
