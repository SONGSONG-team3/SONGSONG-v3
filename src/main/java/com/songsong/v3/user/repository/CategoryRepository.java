package com.songsong.v3.user.repository;

import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c JOIN UserCategory uc ON c.categoryId = uc.category.categoryId WHERE uc.user.userNo = :userNo")
    List<CategoryDto> findCategoriesByUserNo(@Param("userNo") int userNo);
}
