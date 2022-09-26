package com.pokewith.user.controller;

import com.pokewith.user.service.UserService;
import com.pokewith.user.dto.request.RqEmailCheckDto;
import com.pokewith.user.dto.request.RqLogInDto;
import com.pokewith.user.dto.request.RqSignUpDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "email, password, nickname1, friendCode1 로 회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회원가입성공"),
            @ApiResponse(code = 400, message = "잘못된 값으로 요청 유효성검사 실패")
    })
    public ResponseEntity<String> signup (@Valid @RequestBody RqSignUpDto dto) {
        log.info("/signup");
        return userService.normalSignUp(dto);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "email password 로 로그인")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "로그인성공"),
            @ApiResponse(code = 400, message = "email password 불일치, 유효성검사, 소셜로그인 유저가 일반로그인 시도")
    })
    public ResponseEntity<String> login (@Valid @RequestBody RqLogInDto dto, HttpServletResponse response, HttpServletRequest request) {
        log.info("/login");
        return userService.normalLogIn(dto, response, request);
    }

    @PostMapping("/email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "사용가능한 이메일"),
            @ApiResponse(code = 400, message = "유효성검사 실패"),
            @ApiResponse(code = 409, message = "이미 사용중인 이메일")
    })
    public ResponseEntity<String> emailCheck(@Valid @RequestBody RqEmailCheckDto dto) {
        log.info("/email");
        return userService.emailCheck(dto);
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> loginTest() {
        log.info("/user/test");
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }
}
