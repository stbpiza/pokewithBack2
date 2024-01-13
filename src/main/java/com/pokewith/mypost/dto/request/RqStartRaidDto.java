package com.pokewith.mypost.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Schema(name = "모집중 레이드 시작하기")
public class RqStartRaidDto {

    @NotNull
    private Long raidId;

    @NotNull
    @Size(min = 1, max = 5)
    private List<Long> raidCommentIdList = new ArrayList<>();

}
