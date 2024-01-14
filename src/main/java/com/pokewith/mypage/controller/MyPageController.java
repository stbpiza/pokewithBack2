package com.pokewith.mypage.controller;

import com.pokewith.auth.UsernameService;
import com.pokewith.mypage.service.MyPageService;
import com.pokewith.mypage.dto.response.RpGetMyPageDto;
import com.pokewith.mypage.dto.request.RqUpdateMyPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;
    private final UsernameService usernameService;

    @GetMapping("/mypage")
    @Operation(summary = "본인 닉네임 + 친구코드 조회 api", description = "마이페이지에서 본인 닉네임과 친구코드 조회하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 친구코드 조회 성공"),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패(없는 userId)")
    })
    public ResponseEntity<RpGetMyPageDto> getMyPage(HttpServletRequest request) {
        log.info("/api/mypage");
        Long userId = usernameService.getUsername(request);
        return myPageService.getMyPage(userId);
    }

    @PutMapping("/mypage")
    @Operation(summary = "본인 닉네임 + 친구코드 변경 api", description = "마이페이지에서 본인 닉네임과 친구코드 변경하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 친구코드 변경 성공"),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패")
    })
    public ResponseEntity<String> updateMyPage(@Valid @RequestBody RqUpdateMyPageDto dto, HttpServletRequest request) {
        log.info("/api/mypage");
        log.info(String.valueOf(dto));
        Long userId = usernameService.getUsername(request);
        return myPageService.updateMyPage(userId, dto);
    }
}
