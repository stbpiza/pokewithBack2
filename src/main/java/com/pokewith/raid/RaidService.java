package com.pokewith.raid;

import com.pokewith.raid.dto.RpRaidListDto;
import com.pokewith.raid.dto.RqRaidListSearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RaidService {
    ResponseEntity<RpRaidListDto> getRaidList(RqRaidListSearchDto dto, Pageable pageable);
}
