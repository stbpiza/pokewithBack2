package com.pokewith.raid;

import com.pokewith.raid.dto.RqPostRaidDto;
import com.pokewith.superclass.TimeEntity;
import com.pokewith.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Raid extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long raidId;

    @Column(nullable = false)
    private String pokemon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RaidType raidType;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private int normalPass;

    @Column(nullable = false)
    private int remotePass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RaidState raidState;


    private int requiredLevel;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "raid", fetch = FetchType.LAZY)
    private List<RaidComment> raidComments = new ArrayList<>();


    @Builder
    public Raid(RqPostRaidDto dto, User user) {
        this.pokemon = dto.getPokemon();
        this.raidType = dto.getRaidType();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.normalPass = dto.getNormalPass();
        this.remotePass = dto.getRemotePass();
        this.raidState = RaidState.INVITE;
        this.requiredLevel = dto.getRequiredLevel();

        this.user = user;
    }
}
