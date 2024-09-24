package com.songsong.v3.user.repository;

import com.songsong.v3.user.entity.UserCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {


    @Modifying
    @Transactional
    @Query("DELETE FROM UserCategory uc WHERE uc.user.userNo = :userNo")
    void deleteByUserNo(int userNo);

    List<UserCategory> findByUserUserNo(int userNo);

    @Query("SELECT uc.user.userNo FROM UserCategory uc WHERE uc.category.categoryId = :categoryId")
    List<Integer> findUserNoByCategoryId(@Param("categoryId") int categoryId);
}