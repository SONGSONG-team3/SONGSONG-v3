package com.songsong.v3.playlist.service;

import com.songsong.v3.playlist.dto.PlaylistDto;
import com.songsong.v3.playlist.dto.PlaylistResultDto;
import com.songsong.v3.playlist.repository.PlaylistRepository;
import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;


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








}
