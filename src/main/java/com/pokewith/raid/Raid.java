package com.pokewith.raid;

import com.pokewith.superclass.TimeEntity;
import com.pokewith.user.User;
import lombok.AccessLevel;
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
    private int pokemon;

    @Column(nullable = false)
    private String raidLevel;


    private LocalDateTime startTime;


    private LocalDateTime endTime;


    private int normalPass;


    private int remotePass;


    private boolean end;


    private int requiredLevel;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "raid", fetch = FetchType.LAZY)
    private List<RaidComment> raidComments = new ArrayList<>();
}
