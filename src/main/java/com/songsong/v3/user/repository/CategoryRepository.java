package com.songsong.v3.user.repository;

import com.songsong.v3.user.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
