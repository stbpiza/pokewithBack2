package com.pokewith.mypost.dto.response;

import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidState;
import com.pokewith.raid.RaidType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class RpGetMyPostDto {

    private Long raidId;

    private String pokemon;

    private RaidType raidType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int normalPass;

    private int remotePass;

    private RaidState raidState;

    private int requiredLevel;

    private String chat;

    private Long userId;

    private String nickname1;

    private int likeCount;

    private int hateCount;

    private boolean isVote;

    @Builder
    public RpGetMyPostDto(Raid raid, boolean isVote) {
        try {
            this.raidId = raid.getRaidId();
            this.pokemon = raid.getPokemon();
            this.raidType = raid.getRaidType();
            this.startTime = raid.getStartTime();
            this.endTime = raid.getEndTime();
            this.normalPass = raid.getNormalPass();
            this.remotePass = raid.getRemotePass();
            this.raidState = raid.getRaidState();
            this.requiredLevel = raid.getRequiredLevel();
            this.chat = raid.getChat();

            this.userId = raid.getUser().getUserId();
            this.nickname1 = raid.getUser().getNickname1();
            this.likeCount = raid.getUser().getLikeCount();
            this.hateCount = raid.getUser().getHateCount();

            this.isVote = isVote;
        } catch (NullPointerException e) {
            // 작성한 글이나 댓글이 없으면 빈 DTO 리턴
        }
    }
}
