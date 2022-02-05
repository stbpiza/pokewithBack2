package com.pokewith.raid;

import com.pokewith.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RaidComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long raidCommentId;

    private String checkNum;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raidId")
    private Raid raid;
}
