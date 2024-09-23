package com.songsong.v3.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String main() {
        return "main";
    }
    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @GetMapping("/myplaylist")
    public String myplaylist() {
        return "myplaylist";
    }

//    @GetMapping("/otherplaylist")
//    public String otherplaylist() {
//        return "otherplaylist";
//    }

    @GetMapping("/otherplaylist/{userNo}")
    public String otherPlaylistPage(@PathVariable("userNo") int userNo) {
        return "otherplaylist";  // otherplaylist.html 페이지로 이동
    }
}
