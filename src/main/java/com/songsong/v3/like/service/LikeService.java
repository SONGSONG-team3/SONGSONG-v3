package com.songsong.v3.like.service;

import com.songsong.v3.user.entity.User;

public interface LikeService {
    boolean isLikedByUser(User userFrom, User userTo);
    void deleteByUserFromAndUserTo(User userFrom, User userTo);
    void save(User userFrom, User userTo);
}
