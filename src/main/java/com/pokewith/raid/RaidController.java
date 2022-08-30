package com.pokewith.raid;

import com.pokewith.raid.dto.RpRaidListDto;
import com.pokewith.raid.dto.RqRaidListSearchDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RaidController {

    private final RaidService raidService;

    @GetMapping("/raid")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity<RpRaidListDto> getRaidList(RqRaidListSearchDto dto, Pageable pageable) {
        log.info("/raid");
        return raidService.getRaidList(dto, pageable);
    }
}
