package com.songsong.v3.like.repository;

import com.songsong.v3.like.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {

}
