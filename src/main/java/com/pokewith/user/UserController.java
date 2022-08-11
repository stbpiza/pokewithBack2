package com.pokewith.user;

import com.pokewith.user.dto.RqSignUpDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
