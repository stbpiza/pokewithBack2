package com.pokewith.main;

import com.pokewith.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    @ApiOperation(value = "메인 페이지로 이동", notes = "메인 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "메인 페이지로 이동")
    })
    public String home() {
        log.info("/");
        return "index";
    }

    @GetMapping("/index")
    @ApiOperation(value = "메인 페이지로 이동", notes = "메인 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "메인 페이지로 이동")
    })
    public String index() {
        log.info("/index");
        return "index";
    }

    @GetMapping("/join")
    @ApiOperation(value = "페이스북 연동 회원가입 페이지로 이동(미구현)", notes = "페이스북 연동 회원가입 페이지로 이동(미구현)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "페이스북 연동 회원가입 페이지로 이동(미구현)")
    })
    public String join() {
        log.info("/join");
        return "join";
    }

    @GetMapping("/login")
    @ApiOperation(value = "로그인 페이지로 이동", notes = "로그인 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "로그인 페이지로 이동")
    })
    public String login() {
        log.info("/login");
        return "login";
    }

    @GetMapping("/mypage")
    @ApiOperation(value = "마이 페이지로 이동", notes = "마이 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마이 페이지로 이동")
    })
    public String mypage() {
        log.info("/mypage");
        return "mypage";
    }

    @GetMapping("/mypost")
    @ApiOperation(value = "마이 포스트 페이지로 이동", notes = "마이 포스트 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마이 포스트 페이지로 이동")
    })
    public String mypost() {
        log.info("/mypost");
        return "mypost";
    }

    @GetMapping("/register")
    @ApiOperation(value = "회원가입 페이지로 이동", notes = "회원가입 페이지로 이동")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회원가입 페이지로 이동")
    })
    public String register() {
        log.info("/register");
        return "register";
    }

}
