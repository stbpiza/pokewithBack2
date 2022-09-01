package com.pokewith.raid.dto;

import com.pokewith.raid.RaidState;
import com.pokewith.raid.RaidType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("레이드 모집글 작성")
@NoArgsConstructor
@ToString
public class RqPostRaidDto {

    @NotNull
    private int pokemon;

    @NotNull
    private RaidType raidType;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private int normalPass;

    @NotNull
    private int remotePass;

    private int requiredLevel;
}