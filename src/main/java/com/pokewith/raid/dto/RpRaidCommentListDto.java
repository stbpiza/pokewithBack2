package com.pokewith.raid.dto;

import com.pokewith.raid.RaidComment;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("레이드 댓글 리스트 조회")
public class RpRaidCommentListDto {

    private List<RaidCommentDto> raidCommentDtoList = new ArrayList<>();

    @Builder
    public RpRaidCommentListDto(List<RaidComment> raidCommentList) {
        this.raidCommentDtoList = raidCommentList.stream()
                .map(RaidCommentDto::new).collect(Collectors.toList());
    }
}
