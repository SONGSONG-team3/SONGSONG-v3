package com.songsong.v3.user.service;

import com.songsong.v3.user.entity.UserCategory;

import java.util.List;

public interface UserCategoryService {
    List<UserCategory> findByUserNo(int userNo);
}
