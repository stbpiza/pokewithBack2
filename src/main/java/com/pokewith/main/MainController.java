package com.pokewith.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        log.info("/");
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        log.info("/index");
        return "index";
    }

    @GetMapping("/join")
    public String join() {
        log.info("/join");
        return "join";
    }

    @GetMapping("/login")
    public String login() {
        log.info("/login");
        return "login";
    }

    @GetMapping("/mypage")
    public String mypage() {
        log.info("/mypage");
        return "mypage";
    }

    @GetMapping("/mypost")
    public String mypost() {
        log.info("/mypost");
        return "mypost";
    }

    @GetMapping("/register")
    public String register() {
        log.info("/register");
        return "register";
    }

    @GetMapping("/room")
    public String room() {
        log.info("/room");
        return "room";
    }


}
