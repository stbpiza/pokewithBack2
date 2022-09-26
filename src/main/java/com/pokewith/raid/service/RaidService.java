package com.pokewith.raid.service;

import com.pokewith.raid.dto.request.RqPostRaidCommentDto;
import com.pokewith.raid.dto.request.RqPostRaidDto;
import com.pokewith.raid.dto.request.RqRaidListSearchDto;
import com.pokewith.raid.dto.response.RpRaidCommentListDto;
import com.pokewith.raid.dto.response.RpRaidListDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RaidService {
    ResponseEntity<RpRaidListDto> getRaidList(RqRaidListSearchDto dto, Pageable pageable);

    ResponseEntity<String> postRaid(RqPostRaidDto dto, Long userId);

    ResponseEntity<String> postRaidComment(RqPostRaidCommentDto dto, Long userId);

    ResponseEntity<RpRaidCommentListDto> getRaidCommentList(Long raidId);
}
