package com.pokewith.raid.dto;

import com.pokewith.raid.RaidState;
import com.pokewith.raid.RaidType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@ApiModel("레이드 리스트 조회")
public class RqRaidListSearchDto {

    @ApiModelProperty(value = "레이트 타입(1, 3, 5, MEGA)", example = "ONE, THREE, FIVE, MEGA")
    private RaidType type;

    @ApiModelProperty(value = "레이트 타입(모집중, 진행중, 종료)", example = "INVITE, DOING, DONE")
    private RaidState state;
}
