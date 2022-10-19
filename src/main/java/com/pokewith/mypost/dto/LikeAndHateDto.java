package com.pokewith.mypost.dto;

import com.pokewith.user.LikeOrHate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LikeAndHateDto {
    private Long userId;
    private LikeOrHate likeOrHate;
}
