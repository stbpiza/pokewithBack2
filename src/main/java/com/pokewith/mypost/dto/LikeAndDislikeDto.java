package com.pokewith.mypost.dto;

import com.pokewith.user.LikeOrDislike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class LikeAndDislikeDto {

    @NotNull
    private Long userId;

    @NotNull
    private LikeOrDislike likeOrDislike;

}
