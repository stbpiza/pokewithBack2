package com.pokewith.raid;

import com.pokewith.superclass.TimeEntity;
import com.pokewith.user.User;
import lombok.AccessLevel;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raidId")
    private Raid raid;
}
