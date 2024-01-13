package com.pokewith.raid.dto.request;

import com.pokewith.raid.RaidState;
import com.pokewith.raid.RaidType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Schema(name = "레이드 리스트 조회")
public class RqRaidListSearchDto {

    @Schema(description = "레이트 타입(1, 3, 5, MEGA)", example = "ONE, THREE, FIVE, MEGA")
    private RaidType type;

    @Schema(description = "레이트 타입(모집중, 진행중, 종료)", example = "INVITE, DOING, DONE")
    private RaidState state;
}
