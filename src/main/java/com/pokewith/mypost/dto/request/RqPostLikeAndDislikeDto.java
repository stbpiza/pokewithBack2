package com.pokewith.mypost.dto.request;

import com.pokewith.mypost.dto.LikeAndDislikeDto;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@ApiModel("레이드 종료후 좋아요 싫어요")
public class RqPostLikeAndDislikeDto {

    private List<LikeAndDislikeDto> likeAndDislikeDtoList = new ArrayList<>();

}
