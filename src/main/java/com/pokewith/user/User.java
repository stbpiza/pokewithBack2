package com.pokewith.user;

import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidComment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user",
uniqueConstraints = {
    @UniqueConstraint(name = "oauth2Id", columnNames = {"oauth2Id"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 50)
    private String oauth2Id;

    @Column(length = 100)
    private String email;

    @Column(length = 25, nullable = false)
    private String nickname1;

    @Column(length = 20, nullable = false)
    private String friendCode1;

    @Column(length = 25)
    private String nickname2;

    @Column(length = 20)
    private String friendCode2;

    @Column(length = 25)
    private String nickname3;

    @Column(length = 20)
    private String friendCode3;

    @Column(length = 25)
    private String nickname4;

    @Column(length = 20)
    private String friendCode4;

    @Column(length = 25)
    private String nickname5;

    @Column(length = 20)
    private String friendCode5;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Raid> raids = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<RaidComment> raidComments = new ArrayList<>();

}
