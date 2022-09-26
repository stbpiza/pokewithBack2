package com.pokewith.mypost.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@ApiModel("모집중 레이드 시작하기")
public class RqStartRaidDto {

    @NotNull
    private Long raidId;

    @NotNull
    @Size(min = 1, max = 5)
    private List<Long> raidCommentIdList = new ArrayList<>();

    @NotNull
    private String chat;
}
