package com.pokewith.mypage.dto.response;

import com.pokewith.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class RpGetMyPageDto {

    private Long userId;

    private String nickname1;

    private String friendCode1;

    private String nickname2;

    private String friendCode2;

    private String nickname3;

    private String friendCode3;

    private String nickname4;

    private String friendCode4;

    private String nickname5;

    private String friendCode5;

    public RpGetMyPageDto(User user) {
        this.userId = user.getUserId();
        this.nickname1 = user.getNickname1();
        this.friendCode1 = user.getFriendCode1();
        this.nickname2 = user.getNickname2();
        this.friendCode2 = user.getFriendCode2();
        this.nickname3 = user.getNickname3();
        this.friendCode3 = user.getFriendCode3();
        this.nickname4 = user.getNickname4();
        this.friendCode4 = user.getFriendCode4();
        this.nickname5 = user.getNickname5();
        this.friendCode5 = user.getFriendCode5();
    }
}
