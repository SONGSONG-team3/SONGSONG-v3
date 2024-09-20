package com.songsong.v3.user.repository;

import com.songsong.v3.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
