package com.songsong.v3.user.repository;

import com.songsong.v3.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByUserEmail(String email);
    Boolean existsByUserNickname(String nickname);

    User findByUserEmail(String userEmail);
}
