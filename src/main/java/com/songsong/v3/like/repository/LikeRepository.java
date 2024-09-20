package com.songsong.v3.like.repository;

import com.songsong.v3.like.entity.UserLike;
import com.songsong.v3.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeRepository extends JpaRepository<UserLike, Long> {
    boolean existsByUserFromAndUserTo(User userFrom, User userTo);
    void deleteByUserFromAndUserTo(User userFrom, User userTo);
}