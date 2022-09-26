package com.pokewith.raid.dto;

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

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int normalPass;

    private int remotePass;

    private RaidState raidState;

    private int requiredLevel;

    private String nickname1;

    private int likeCount;

    private int hateCount;

    public RaidDto(Raid raid) {
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

            this.nickname1 = raid.getUser().getNickname1();
            this.likeCount = raid.getUser().getLikeCount();
            this.hateCount = raid.getUser().getHateCount();
        } catch (NullPointerException e) {
            // 작성한 글이나 댓글이 없으면 빈 DTO 리턴
        }
    }
}
