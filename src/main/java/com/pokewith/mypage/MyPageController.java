package com.pokewith.mypage;

import com.pokewith.mypage.dto.RpGetMyPageDto;
import com.pokewith.mypage.dto.RqUpdateMyPageDto;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "닉네임 친구코드 조회 성공"),
            @ApiResponse(code = 400, message = "유효성검사 실패(없는 userId)")
    })
    public ResponseEntity<RpGetMyPageDto> getMyPage(HttpServletRequest request) {
        log.info("/api/mypage");
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        return myPageService.getMyPage(userId);
    }

    @PostMapping("/mypage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "닉네임 친구코드 변경 성공"),
            @ApiResponse(code = 400, message = "유효성검사 실패")
    })
    public ResponseEntity<String> updateMyPage(@Valid @RequestBody RqUpdateMyPageDto dto, HttpServletRequest request) {
        log.info("/api/mypage");
        log.info("dto", dto);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        return myPageService.updateMyPage(userId, dto);
    }
}
