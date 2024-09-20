package com.songsong.v3.like.service;

import com.songsong.v3.like.entity.UserLike;
import com.songsong.v3.like.repository.LikeRepository;
import com.songsong.v3.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public boolean isLikedByUser(User userFrom, User userTo){
        return likeRepository.existsByUserFromAndUserTo(userFrom, userTo);
    }

    @Override
    @Transactional
    public void deleteByUserFromAndUserTo(User userFrom, User userTo) {
        likeRepository.deleteByUserFromAndUserTo(userFrom, userTo);
    }

    @Override
    public void save(User userFrom, User userTo) {
        UserLike userLike = new UserLike();
        userLike.setUserFrom(userFrom);
        userLike.setUserTo(userTo);
        likeRepository.save(userLike);
    }
}
