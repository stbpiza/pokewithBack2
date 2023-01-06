package com.pokewith.mypost.dto;

import com.pokewith.user.LikeOrDislike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LikeAndDislikeDto {
    private Long userId;
    private LikeOrDislike likeOrDislike;
}
