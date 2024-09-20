package com.songsong.v3.user.service;

import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.entity.User;

public interface UserService {
    public User findByUserNo(int userNo);
}
