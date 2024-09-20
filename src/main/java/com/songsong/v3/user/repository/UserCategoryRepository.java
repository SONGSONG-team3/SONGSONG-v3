package com.songsong.v3.user.repository;

import com.songsong.v3.user.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findByUserUserNo(int userNo);
}