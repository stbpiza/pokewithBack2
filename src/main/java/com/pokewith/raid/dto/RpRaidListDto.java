package com.pokewith.raid.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("레이드 리스트 조회")
public class RpRaidListDto {

    private List<RaidDto> raidDtoList = new ArrayList<>();

    private int totalPages;

    private Long totalCount;

    public RpRaidListDto(List<RaidDto> raidDtoList, int totalPages, Long totalCount) {
        this.raidDtoList = raidDtoList;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }
}
