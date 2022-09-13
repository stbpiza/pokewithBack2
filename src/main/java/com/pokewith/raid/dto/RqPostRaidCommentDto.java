package com.pokewith.raid.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("레이드 모집글에 댓글 작성")
@NoArgsConstructor
public class RqPostRaidCommentDto {

    @NotNull
    private Long raidId;

    @NotNull
    private boolean account1;

    @NotNull
    private boolean account2;

    @NotNull
    private boolean account3;

    @NotNull
    private boolean account4;

    @NotNull
    private boolean account5;
}
