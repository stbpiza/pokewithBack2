package com.pokewith.mypost.dto.response;

import com.pokewith.raid.dto.RaidDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RpGetMyPostDto {

    RaidDto raidDto;

    @Builder
    public RpGetMyPostDto(RaidDto raidDto) {
        this.raidDto = raidDto;
    }
}
