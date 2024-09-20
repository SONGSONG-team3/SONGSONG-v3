package com.songsong.v3.user.service;

import com.songsong.v3.user.dto.UserDto;
import com.songsong.v3.user.entity.User;
import com.songsong.v3.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserNo(int userNo) {
        return userRepository.findByUserNo(userNo);
    }

}
