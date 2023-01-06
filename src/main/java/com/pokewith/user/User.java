package com.pokewith.user;

import com.pokewith.mypage.dto.request.RqUpdateMyPageDto;
import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidComment;
import com.pokewith.superclass.TimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
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
    @UniqueConstraint(name = "oauth2Id", columnNames = {"oauth2Id"}),
    @UniqueConstraint(name = "email", columnNames = {"email"})
})
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 50)
    private String oauth2Id;

    @Column(length = 100)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private UserType userType;

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

    private int likeCount;

    private int dislikeCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private UserState userState;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Raid> raids = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<RaidComment> raidComments = new ArrayList<>();

    @Builder(builderClassName = "NormalSignUpBuilder", builderMethodName = "NormalSignUpBuilder")
    public User(String email, String password, String nickname1, String friendCode1) {
        this.email = email;
        this.password = password;
        this.nickname1 = nickname1;
        this.friendCode1 = friendCode1;
        this.userType = UserType.ROLE_NOTUSER;
        this.userState = UserState.FREE;
    }

    public void EmailCheck() {
        this.userType = UserType.ROLE_USER;
    }

    public void setPostState() {
        this.userState = UserState.POST;
    }

    public void setCommentState() {
        this.userState = UserState.COMMENT;
    }

    public void setFreeState() {
        this.userState = UserState.FREE;
    }

    public void updateUser(RqUpdateMyPageDto dto) {
        this.nickname1 = dto.getNickname1();
        this.friendCode1 = dto.getFriendCode1();
        this.nickname2 = dto.getNickname2();
        this.friendCode2 = dto.getFriendCode2();
        this.nickname3 = dto.getNickname3();
        this.friendCode3 = dto.getFriendCode3();
        this.nickname4 = dto.getNickname4();
        this.friendCode4 = dto.getFriendCode4();
        this.nickname5 = dto.getNickname5();
        this.friendCode5 = dto.getFriendCode5();
    }

    public User(Long userId){
        this.userId = userId;
    }

    public String getUserIdToString() { return Long.toString(userId); }
}
