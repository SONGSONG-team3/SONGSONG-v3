package com.songsong.v3.user.repository;

import com.songsong.v3.user.dto.CategoryDto;
import com.songsong.v3.user.entity.UserCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UserCategory uc WHERE uc.user.userNo = :userNo")
    void deleteByUserNo(int userNo);

}
