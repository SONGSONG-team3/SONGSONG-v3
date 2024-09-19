package com.songsong.v3.user.service;

import com.songsong.v3.user.dto.UserSignupRequestDto;
import com.songsong.v3.user.entity.Category;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.common.CommonResponse;
import com.songsong.v3.user.dto.UserSignupResultDto;
import com.songsong.v3.user.entity.UserCategory;
import com.songsong.v3.user.repository.CategoryRepository;
import com.songsong.v3.user.repository.UserCategoryRepository;
import com.songsong.v3.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

   // 1. 회원가입
    public UserSignupResultDto signup(UserSignupRequestDto userSignupRequestDto) {
        LOGGER.info("회원가입 수행");
        UserSignupResultDto userSignupResultDto = new UserSignupResultDto();

        // 1-1. 중복 이메일, 닉네임 확인
        String userEmail = userSignupRequestDto.getUserDto().getUserEmail();
        String userNickname = userSignupRequestDto.getUserDto().getUserNickname();

        Boolean isExistsEmail = userRepository.existsByUserEmail(userEmail);
        Boolean isExistsNickname = userRepository.existsByUserNickname(userNickname);

        if (isExistsEmail) {
            LOGGER.info("[회원가입 실패] 이메일 중복");
            setFailResult(userSignupResultDto);
            userSignupResultDto.setMsg("Email already exists");
            return userSignupResultDto;
        }

        if (isExistsNickname) {
            LOGGER.info("[회원가입 실패] 닉네임 중복");
            setFailResult(userSignupResultDto);
            userSignupResultDto.setMsg("Nickname already exists");
            return userSignupResultDto;
        }

        // 1-2. 회원 가입 진행
        User user = User.builder()
                .userName(userSignupRequestDto.getUserDto().getUserName())
                .userPassword(bCryptPasswordEncoder.encode(userSignupRequestDto.getUserDto().getUserPassword()))
                .userEmail(userSignupRequestDto.getUserDto().getUserEmail())
                .userNickname(userSignupRequestDto.getUserDto().getUserNickname())
                .userImage(userSignupRequestDto.getUserDto().getUserImage())
                .userRegisterDate(LocalDateTime.now())
                .userLike(0)
                .build();

        User savedUser = userRepository.save(user);

        // 카테고리 저장
        List<Integer> categoryIds = userSignupRequestDto.getCategories();
        Set<UserCategory> userCategories = categoryIds.stream()
                .map(categoryId -> {
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    return new UserCategory(savedUser, category);
                })
                .collect(Collectors.toSet());

        userCategoryRepository.saveAll(userCategories);

        if (!savedUser.getUserEmail().isEmpty()) {
            LOGGER.info("회원가입 성공");
            setSuccessResult(userSignupResultDto);
        } else {
            LOGGER.info("회원가입 실패");
            setFailResult(userSignupResultDto);
        }

        return userSignupResultDto;
    }

    private void setSuccessResult(UserSignupResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(UserSignupResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}
