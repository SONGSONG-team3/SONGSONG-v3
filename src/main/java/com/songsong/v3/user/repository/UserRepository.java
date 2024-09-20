package com.songsong.v3.user.repository;

import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
        SELECT new com.songsong.v3.user.dto.CategoryDto(c.categoryId, c.categoryName)
        FROM UserCategory uc
        JOIN uc.category c
        WHERE uc.user.userNo = :userNo
       """)
    List<CategoryDto> selectCategoriesByUserNo(@Param("userNo") int userNo);

    Boolean existsByUserEmail(String email);

    Boolean existsByUserNickname(String nickname);

    User findByUserEmail(String userEmail);
}