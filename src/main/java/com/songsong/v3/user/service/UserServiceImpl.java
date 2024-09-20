//package com.songsong.v3.user.service;
//
//import com.songsong.v3.user.dto.UserCategoryDto;
//import com.songsong.v3.user.dto.UserDto;
//import com.songsong.v3.user.dto.UserResultDto;
//import com.songsong.v3.user.entity.Category;
//import com.songsong.v3.user.entity.User;
//import com.songsong.v3.user.entity.UserCategory;
//import com.songsong.v3.user.repository.CategoryRepository;
//import com.songsong.v3.user.repository.UserCategoryRepository;
//import com.songsong.v3.user.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//    private final UserCategoryRepository userCategoryRepository;
//    private final CategoryRepository categoryRepository;
//
//
//    @Override
//    public UserResultDto detailMypage(int userNo) {
//        UserResultDto userResultDto = new UserResultDto();
//
//        Optional<User> optionalUser = userRepository.findById(userNo);
//
//        optionalUser.ifPresentOrElse(
//                user -> {
//                    UserDto userDto = new UserDto();
//                    userDto.setUserNo(user.getUserNo());
//                    userDto.setUserName(user.getUserName());
//                    userDto.setUserEmail(user.getUserEmail());
//                    userDto.setUserPassword(user.getUserPassword());
//                    userDto.setUserNickname(user.getUserNickname());
//                    userDto.setUserImage(user.getUserImage());
//
//                    List<UserCategoryDto> userCategoryDtoList = new ArrayList<>();
//                    user.getUserCategories().forEach(userCategory -> {
//                        UserCategoryDto userCategoryDto = new UserCategoryDto();
//                        userCategoryDto.setUserNo(user.getUserNo());
//                        userCategoryDto.setCategoryId(userCategory.getCategory().getCategoryId());
//                        userCategoryDtoList.add(userCategoryDto);
//                    });
//                    userDto.setUserCategoryDtoList(userCategoryDtoList);
//
//                    userResultDto.setUserDto(userDto);
//                    userResultDto.setResult("success");
//                },
//                () -> {
//                    userResultDto.setResult("fail");
//                }
//        );
//
//        return userResultDto;
//    }
//
//    @Override
//    @Transactional
//    public UserResultDto updateUserMypage(UserDto userDto) {
//        UserResultDto userResultDto = new UserResultDto();
//
//        try{
//
//            Optional<User> optionalUser = userRepository.findById(userDto.getUserNo());
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//
//                // 기본적인 사용자 정보 업데이트
//                // 비밀번호 , 닉네임 수정 가능
//                user.setUserPassword(userDto.getUserPassword());
//                user.setUserNickname(userDto.getUserNickname());
//
//                userRepository.save(user);
//                userResultDto.setResult("success");
//
//            } else{
//                userResultDto.setResult("fail");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            userResultDto.setResult("fail");
//        }
//
//
//        return userResultDto;
//    }
//
//
//    @Override
//    @Transactional
//    public UserResultDto updateUserCategory(int userNo, List<Integer> categoryIds) {
//        UserResultDto userResultDto = new UserResultDto();
//
//        try {
//            // Delete existing user categories
//            userCategoryRepository.deleteByUserNo(userNo);
//
//            // Fetch the user entity
//            User user = userRepository.findById(userNo)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            // Fetch existing categories
//            List<Category> categories = categoryRepository.findAllById(categoryIds);
//
//            // Add new user categories
//            List<UserCategory> newUserCategories = categories.stream()
//                    .map(category -> {
//                        UserCategory userCategory = new UserCategory();
//                        userCategory.setUser(user);
//                        userCategory.setCategory(category);
//                        return userCategory;
//                    })
//                    .collect(Collectors.toList());
//
//            userCategoryRepository.saveAll(newUserCategories);
//
//            userResultDto.setResult("success");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            userResultDto.setResult("fail");
//        }
//
//        return userResultDto;
//    }/**/
//
//
//}
