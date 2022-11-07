package com.pokewith.mypost.dto;

import com.pokewith.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MyPostMemberDto {

    private Long userId;
    private String nickname1;

    public MyPostMemberDto(User user) {
        this.userId = user.getUserId();
        this.nickname1 = user.getNickname1();
    }
}
