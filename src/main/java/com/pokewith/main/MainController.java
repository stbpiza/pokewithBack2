package com.pokewith.main;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    @Operation(summary = "메인 페이지로 이동", description = "메인 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인 페이지로 이동")
    })
    public String home() {
        log.info("/");
        return "index";
    }

    @GetMapping("/index")
    @Operation(summary = "메인 페이지로 이동", description = "메인 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인 페이지로 이동")
    })
    public String index() {
        log.info("/index");
        return "index";
    }

    @GetMapping("/join")
    @Operation(summary = "페이스북 연동 회원가입 페이지로 이동(미구현)", description = "페이스북 연동 회원가입 페이지로 이동(미구현)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "페이스북 연동 회원가입 페이지로 이동(미구현)")
    })
    public String join() {
        log.info("/join");
        return "join";
    }

    @GetMapping("/login")
    @Operation(summary = "로그인 페이지로 이동", description = "로그인 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 페이지로 이동")
    })
    public String login() {
        log.info("/login");
        return "login";
    }

    @GetMapping("/mypage")
    @Operation(summary = "마이 페이지로 이동", description = "마이 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이 페이지로 이동")
    })
    public String mypage() {
        log.info("/mypage");
        return "mypage";
    }

    @GetMapping("/mypost")
    @Operation(summary = "마이 포스트 페이지로 이동", description = "마이 포스트 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이 포스트 페이지로 이동")
    })
    public String mypost() {
        log.info("/mypost");
        return "mypost";
    }

    @GetMapping("/register")
    @Operation(summary = "회원가입 페이지로 이동", description = "회원가입 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 페이지로 이동")
    })
    public String register() {
        log.info("/register");
        return "register";
    }

}
