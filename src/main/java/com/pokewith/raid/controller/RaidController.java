package com.pokewith.raid.controller;

import com.pokewith.auth.UsernameService;
import com.pokewith.raid.service.RaidService;
import com.pokewith.raid.dto.request.RqPostRaidCommentDto;
import com.pokewith.raid.dto.request.RqPostRaidDto;
import com.pokewith.raid.dto.request.RqRaidListSearchDto;
import com.pokewith.raid.dto.response.RpRaidCommentListDto;
import com.pokewith.raid.dto.response.RpRaidListDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
public class RaidController {

    private final RaidService raidService;
    private final UsernameService usernameService;

    @GetMapping("/raid")
    @ApiOperation(value = "레이드 리스트 조회용 api", notes = "메인 페이지에서 레이드 리스트 조회용 api")
    public ResponseEntity<RpRaidListDto> getRaidList(RqRaidListSearchDto dto, Pageable pageable) {
        log.info("/api/raid");
        return raidService.getRaidList(dto, pageable);
    }

    @PostMapping("/raid")
    @ApiOperation(value = "레이드 게시물 작성용 api", notes = "메인 페이지에서 레이드 게시물 작성용 api")
    public ResponseEntity<String> postRaid(@Valid @RequestBody RqPostRaidDto dto, HttpServletRequest request) {
        log.info("/api/raid");
        Long userId = usernameService.getUsername(request);
        return raidService.postRaid(dto, userId);
    }

    @PostMapping("/comment")
    @ApiOperation(value = "레이드 참여 댓글 작성용 api", notes = "레이드 참여 댓글 작성용 api")
    public ResponseEntity<String> postRaidComment(@Valid @RequestBody RqPostRaidCommentDto dto, HttpServletRequest request) {
        log.info("/api/comment");
        Long userId = usernameService.getUsername(request);
        return raidService.postRaidComment(dto, userId);
    }

    @GetMapping("/comment/{raidId}")
    @ApiOperation(value = "레이드 참여 댓글 조회용 api", notes = "레이드 참여 댓글 조회용 api")
    public ResponseEntity<RpRaidCommentListDto> getRaidCommentList(@PathVariable("raidId") Long raidId) {
        log.info("/api/comment/" + raidId);
        return raidService.getRaidCommentList(raidId);
    }
}
