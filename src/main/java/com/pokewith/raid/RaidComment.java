package com.pokewith.raid;

import com.pokewith.raid.dto.request.RqPostRaidCommentDto;
import com.pokewith.superclass.TimeEntity;
import com.pokewith.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RaidComment extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long raidCommentId;

    private boolean account1;
    private boolean account2;
    private boolean account3;
    private boolean account4;
    private boolean account5;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RaidCommentState raidCommentState;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raidId")
    private Raid raid;


    @Builder
    public RaidComment(RqPostRaidCommentDto dto, User user, Raid raid) {
        this.account1 = dto.isAccount1();
        this.account2 = dto.isAccount2();
        this.account3 = dto.isAccount3();
        this.account4 = dto.isAccount4();
        this.account5 = dto.isAccount5();
        this.user = user;
        this.raid = raid;
        this.raidCommentState = RaidCommentState.WAITING;
    }

    public void joinedComment() {
        this.raidCommentState = RaidCommentState.JOINED;
    }

    public void rejectedComment() {
        this.raidCommentState = RaidCommentState.REJECTED;
    }

    public void endComment() {
        this.raidCommentState = RaidCommentState.END;
    }
}
