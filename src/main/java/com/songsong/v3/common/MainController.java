package com.songsong.v3.common;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/api/v3/main/")
    public String mainPage() {
        return "redirect:/mainpage.html";
    }
}
