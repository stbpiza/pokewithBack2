package com.pokewith.raid;

import com.pokewith.raid.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RaidService {
    ResponseEntity<RpRaidListDto> getRaidList(RqRaidListSearchDto dto, Pageable pageable);

    ResponseEntity<String> postRaid(RqPostRaidDto dto, Long userId);

    ResponseEntity<String> postRaidComment(RqPostRaidCommentDto dto, Long userId);

    ResponseEntity<RpRaidCommentListDto> getRaidCommentList(Long raidId);
}
