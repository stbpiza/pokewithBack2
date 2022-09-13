package com.pokewith.raid;

import com.pokewith.auth.UsernameService;
import com.pokewith.raid.dto.RpRaidListDto;
import com.pokewith.raid.dto.RqPostRaidCommentDto;
import com.pokewith.raid.dto.RqPostRaidDto;
import com.pokewith.raid.dto.RqRaidListSearchDto;
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
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<RpRaidListDto> getRaidList(RqRaidListSearchDto dto, Pageable pageable) {
        log.info("/api/raid");
        return raidService.getRaidList(dto, pageable);
    }

    @PostMapping("/raid")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<String> postRaid(@Valid @RequestBody RqPostRaidDto dto, HttpServletRequest request) {
        log.info("/api/raid");
        Long userId = usernameService.getUsername(request);
        return raidService.postRaid(dto, userId);
    }

    @PostMapping("/comment")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<String> postRaidComment(@Valid @RequestBody RqPostRaidCommentDto dto, HttpServletRequest request) {
        log.info("/api/comment");
        Long userId = usernameService.getUsername(request);
        return raidService.postRaidComment(dto, userId);
    }
}
