package com.pokewith.raid.dto;

import com.pokewith.raid.RaidComment;
import com.pokewith.raid.RaidCommentState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RaidCommentDto {
    private Long raidCommentId;

    private boolean account1;
    private boolean account2;
    private boolean account3;
    private boolean account4;
    private boolean account5;
    private RaidCommentState raidCommentState;

    private Long userId;
    private String nickname1;
    private String nickname2;
    private String nickname3;
    private String nickname4;
    private String nickname5;

    private String friendCode1;
    private String friendCode2;
    private String friendCode3;
    private String friendCode4;
    private String friendCode5;

    public RaidCommentDto(RaidComment raidComment) {
        this.raidCommentId = raidComment.getRaidCommentId();
        this.account1 = raidComment.isAccount1();
        this.account2 = raidComment.isAccount2();
        this.account3 = raidComment.isAccount3();
        this.account4 = raidComment.isAccount4();
        this.account5 = raidComment.isAccount5();
        this.raidCommentState = raidComment.getRaidCommentState();

        this.userId = raidComment.getUser().getUserId();
        this.nickname1 = raidComment.getUser().getNickname1();
        this.nickname2 = raidComment.getUser().getNickname2();
        this.nickname3 = raidComment.getUser().getNickname3();
        this.nickname4 = raidComment.getUser().getNickname4();
        this.nickname5 = raidComment.getUser().getNickname5();

        if (raidComment.getRaidCommentState().equals(RaidCommentState.JOINED)) {
            if(account1) this.friendCode1 = raidComment.getUser().getFriendCode1();
            if(account2) this.friendCode2 = raidComment.getUser().getFriendCode2();
            if(account3) this.friendCode3 = raidComment.getUser().getFriendCode3();
            if(account4) this.friendCode4 = raidComment.getUser().getFriendCode4();
            if(account5) this.friendCode5 = raidComment.getUser().getFriendCode5();
        }
    }
}
