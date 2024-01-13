package com.pokewith.mypost.controller;

import com.pokewith.auth.UsernameService;
import com.pokewith.mypost.dto.request.RqPostLikeAndDislikeDto;
import com.pokewith.mypost.dto.request.RqStartRaidDto;
import com.pokewith.mypost.service.MyPostService;
import com.pokewith.mypost.dto.response.RpGetMyPostDto;
import com.pokewith.valid.CustomValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPostController {

    private final UsernameService usernameService;
    private final MyPostService myPostService;
    private final CustomValidator validator;

    @GetMapping("/mypost")
    @Operation(summary = "마이포스트 본인 작성글 or 댓글 단 작성글 조회 api", description = "본인이 직접 작성한 게시물 혹은 댓글을 단 게시물 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이포스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "작성한 글이나 댓글 없음")
    })
    public ResponseEntity<RpGetMyPostDto> getMyPost(HttpServletRequest request) {
        log.info("/api/mypost");
        Long userId = usernameService.getUsername(request);
        return myPostService.getMyPost(userId);
    }

    @PostMapping("/mypost/start")
    @Operation(summary = "본인 작성글 레이드 시작 api", description = "초대할 댓글 선택해서 레이드 시작하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "레이드 시작 성공"),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "없는 id로 요청")
    })
    public ResponseEntity<String> startRaid(@RequestBody @Valid RqStartRaidDto dto, HttpServletRequest request) {
        log.info("/api/mypost/start");
        Long userId = usernameService.getUsername(request);
        return myPostService.startRaid(dto, userId);
    }

    @DeleteMapping("/mypost/{raidId}")
    @Operation(summary = "본인 작성글 레이드 종료 api", description = "레이드 끝난 후 상태를 end로 변경하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "레이드 종료 성공"),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패(이미 종료된 레이드)"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "없는 id로 요청")
    })
    public ResponseEntity<String> endRaid(@PathVariable("raidId") Long raidId, HttpServletRequest request) {
        log.info("/api/mypost/raidId");
        Long userId = usernameService.getUsername(request);
        return myPostService.endRaid(raidId, userId);
    }

    @DeleteMapping("/mypost/comment")
    @Operation(summary = "본인 작성댓글 종료 api", description = "본인 댓글만 종료하는 api / 초대중인경우 완전종료, 진행중인경우 좋아요싫어요 상태로 변환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 종료 성공"),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패(이미 종료된 댓글)"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity<String> endOneComment(HttpServletRequest request) {
        log.info("/api/mypost/comment");
        Long userId = usernameService.getUsername(request);
        return myPostService.endRaidOneComment(userId);
    }

    @PostMapping("/mypost/vote")
    @Operation(summary = "본인 작성글 레이드 종료 후 좋아요 싫어요 api", description = "레이드 끝난 후 팀원들에게 좋아요 싫어요 주는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 싫어요 성공"),
            @ApiResponse(responseCode = "400", description = "유효성검사 실패(이미 투표한 경우)"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "없는 id로 요청")
    })
    public ResponseEntity<String> voteLikeOrDislike(@RequestBody @Valid RqPostLikeAndDislikeDto dto, HttpServletRequest request
            , BindingResult bindingResult) throws BindException {
        log.info("/api/mypost/vote");

        collectionValidation(dto.getLikeAndDislikeDtoList(), bindingResult);

        Long userId = usernameService.getUsername(request);

        return myPostService.postLikeAndDislike(userId, dto);
    }

    /**
     *  분리한 메소드
     **/

    public void collectionValidation(Collection<?> collection, BindingResult bindingResult) throws BindException {
        validator.validate(collection, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

    }
}
