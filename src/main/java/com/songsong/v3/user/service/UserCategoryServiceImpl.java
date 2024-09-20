package com.songsong.v3.user.service;

import com.songsong.v3.user.entity.UserCategory;
import com.songsong.v3.user.repository.UserCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryServiceImpl implements UserCategoryService{

    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryServiceImpl(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    @Override
    public List<UserCategory> findByUserNo(int userNo) {
        return userCategoryRepository.findByUserUserNo(userNo);
    }
}
