package com.songsong.v3.user.repository;

import com.songsong.v3.user.entity.Category;
import com.songsong.v3.user.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {
}
