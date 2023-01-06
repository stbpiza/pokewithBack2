package com.pokewith.raid.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidState;
import com.pokewith.raid.RaidType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class RaidDto {

    private Long raidId;

    private String pokemon;

    private RaidType raidType;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private int normalPass;

    private int remotePass;

    private RaidState raidState;

    private int requiredLevel;

    private String chat;

    private Long userId;

    private String nickname1;

    private int likeCount;

    private int dislikeCount;

    public RaidDto(Raid raid) {
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
        this.dislikeCount = raid.getUser().getDislikeCount();
    }
}
