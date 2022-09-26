package com.pokewith.mypost.controller;

import com.pokewith.auth.UsernameService;
import com.pokewith.mypost.dto.request.RqStartRaidDto;
import com.pokewith.mypost.service.MyPostService;
import com.pokewith.mypost.dto.response.RpGetMyPostDto;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPostController {

    private final UsernameService usernameService;
    private final MyPostService myPostService;

    @GetMapping("/mypost")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마이포스트 조회 성공"),
            @ApiResponse(code = 404, message = "작성한 글이나 댓글 없음")
    })
    public ResponseEntity<RpGetMyPostDto> getMyPost(HttpServletRequest request) {
        log.info("/api/mypost");
        Long userId = usernameService.getUsername(request);
        return myPostService.getMyPost(userId);
    }

    @PostMapping("/mypost/start")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "레이드 시작 성공"),
            @ApiResponse(code = 400, message = "유효성검사 실패"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 404, message = "없는 id로 요청")
    })
    public ResponseEntity<String> startRaid(@RequestBody @Valid RqStartRaidDto dto, HttpServletRequest request) {
        log.info("/api/mypost/start");
        Long userId = usernameService.getUsername(request);
        return myPostService.startRaid(dto, userId);
    }
}
