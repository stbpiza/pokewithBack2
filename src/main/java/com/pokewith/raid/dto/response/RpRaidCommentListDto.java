package com.pokewith.raid.dto.response;

import com.pokewith.raid.RaidComment;
import com.pokewith.raid.dto.RaidCommentDto;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "레이드 댓글 리스트 조회")
public class RpRaidCommentListDto {

    private List<RaidCommentDto> raidCommentDtoList = new ArrayList<>();

    @Builder
    public RpRaidCommentListDto(List<RaidComment> raidCommentList) {
        this.raidCommentDtoList = raidCommentList.stream()
                .map(RaidCommentDto::new).collect(Collectors.toList());
    }
}
