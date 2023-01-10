package com.pokewith.mypost.controller;

import com.pokewith.auth.UsernameService;
import com.pokewith.mypost.dto.request.RqPostLikeAndDislikeDto;
import com.pokewith.mypost.dto.request.RqStartRaidDto;
import com.pokewith.mypost.service.MyPostService;
import com.pokewith.mypost.dto.response.RpGetMyPostDto;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "마이포스트 본인 작성글 or 댓글 단 작성글 조회 api", notes = "본인이 직접 작성한 게시물 혹은 댓글을 단 게시물 조회")
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
    @ApiOperation(value = "본인 작성글 레이드 시작 api", notes = "초대할 댓글 선택해서 레이드 시작하는 api")
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

    @DeleteMapping("/mypost/{raidId}")
    @ApiOperation(value = "본인 작성글 레이드 종료 api", notes = "레이드 끝난 후 상태를 end로 변경하는 api")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "레이드 종료 성공"),
            @ApiResponse(code = 400, message = "유효성검사 실패(이미 종료된 레이드)"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 404, message = "없는 id로 요청")
    })
    public ResponseEntity<String> endRaid(@PathVariable("raidId") Long raidId, HttpServletRequest request) {
        log.info("/api/mypost/raidId");
        Long userId = usernameService.getUsername(request);
        return myPostService.endRaid(raidId, userId);
    }

    @PostMapping("/mypost/vote")
    @ApiOperation(value = "본인 작성글 레이드 종료 후 좋아요 싫어요 api", notes = "레이드 끝난 후 팀원들에게 좋아요 싫어요 주는 api")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "좋아요 싫어요 성공"),
            @ApiResponse(code = 400, message = "유효성검사 실패(이미 투표한 경우)"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 404, message = "없는 id로 요청")
    })
    public ResponseEntity<String> voteLikeOrDislike(RqPostLikeAndDislikeDto dto, HttpServletRequest request) {
        log.info("/api/mypost/vote");
        Long userId = usernameService.getUsername(request);
        return myPostService.postLikeAndDislike(userId, dto);
    }
}
