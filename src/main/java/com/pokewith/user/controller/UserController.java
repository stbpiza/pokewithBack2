package com.pokewith.user.controller;

import com.pokewith.user.service.UserService;
import com.pokewith.user.dto.request.RqEmailCheckDto;
import com.pokewith.user.dto.request.RqLogInDto;
import com.pokewith.user.dto.request.RqSignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "email, password, nickname1, friendCode1 로 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 값으로 요청 유효성검사 실패")
    })
    public ResponseEntity<String> signup(@Valid @RequestBody RqSignUpDto dto) {
        log.info("/signup");
        return userService.normalSignUp(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "email password 로 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인성공"),
            @ApiResponse(responseCode = "400", description = "email password 불일치, 유효성검사, 소셜로그인 유저가 일반로그인 시도")
    })
    public ResponseEntity<String> login(@Valid @RequestBody RqLogInDto dto, HttpServletResponse response, HttpServletRequest request) {
        log.info("/login");
        return userService.normalLogIn(dto, response, request);
    }

    @PostMapping("/email")
    @Operation(summary = "이메일 중복 확인", description = "email 중복 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용가능한 이메일"),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패"),
            @ApiResponse(responseCode = "409", description = "이미 사용중인 이메일")
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
