package com.pokewith.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @GetMapping("/mypost")
    public String mypost() {
        return "mypost";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/room")
    public String room() {
        return "room";
    }


}
