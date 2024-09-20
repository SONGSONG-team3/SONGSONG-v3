package com.songsong.v3.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/otherplaylist")
    public String otherplaylist() {
        return "otherplaylist";
    }
}
