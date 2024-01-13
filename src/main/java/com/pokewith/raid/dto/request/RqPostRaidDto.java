package com.pokewith.raid.dto.request;

import com.pokewith.raid.RaidType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name="레이드 모집글 작성")
@NoArgsConstructor
public class RqPostRaidDto {

    @NotNull
    private String pokemon;

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
