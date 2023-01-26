package com.pokewith.user.entity;

import com.pokewith.mypage.dto.request.RqUpdateMyPageDto;
import com.pokewith.user.User;
import com.pokewith.user.UserState;
import com.pokewith.user.UserType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTest {

    private final String email = "abc@abc.com";
    private final String password = "1111";
    private final String nickname = "tester";
    private final String friendCode = "0000-0000-0000-0000";


    @Test
    void NormalSignUp_Builder_테스트() {

        // 테스트
        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();


        // 확인
        assertThat(user.getUserType(), is(equalTo(UserType.ROLE_NOTUSER)));
        assertThat(user.getUserState(), is(equalTo(UserState.FREE)));
        assertThat(user.getEmail(), is(equalTo(email)));
        assertThat(user.getPassword(), is(equalTo(password)));
        assertThat(user.getNickname1(), is(equalTo(nickname)));
        assertThat(user.getFriendCode1(), is(equalTo(friendCode)));
    }

    @Test
    void EmailCheck_테스트() {

        // 준비
        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();


        // 테스트
        user.EmailCheck();


        // 확인
        assertThat(user.getUserType(), is(equalTo(UserType.ROLE_USER)));

    }

    @Test
    void setPostState_테스트() {

        // 준비
        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();


        // 테스트
        user.setPostState();


        // 확인
        assertThat(user.getUserState(), is(equalTo(UserState.POST)));
    }

    @Test
    void setCommentState_테스트() {

        // 준비
        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();


        // 테스트
        user.setCommentState();


        // 확인
        assertThat(user.getUserState(), is(equalTo(UserState.COMMENT)));
    }

    @Test
    void setFreeState_테스트() {

        // 준비
        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        user.setPostState();


        // 테스트
        user.setFreeState();


        // 확인
        assertThat(user.getUserState(), is(equalTo(UserState.FREE)));
    }

    @Test
    void updateUser_테스트() {


        // 준비
        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        String nickname1 = "tester1";
        String nickname2 = "tester2";
        String nickname3 = "tester3";
        String nickname4 = "tester4";
        String nickname5 = "tester5";
        String friendCode1 = "0001-0000-0000-0000";
        String friendCode2 = "0002-0000-0000-0000";
        String friendCode3 = "0003-0000-0000-0000";
        String friendCode4 = "0004-0000-0000-0000";
        String friendCode5 = "0005-0000-0000-0000";

        RqUpdateMyPageDto dto = new RqUpdateMyPageDto();
        dto.setFriendCode1(friendCode1);
        dto.setFriendCode2(friendCode2);
        dto.setFriendCode3(friendCode3);
        dto.setFriendCode4(friendCode4);
        dto.setFriendCode5(friendCode5);
        dto.setNickname1(nickname1);
        dto.setNickname2(nickname2);
        dto.setNickname3(nickname3);
        dto.setNickname4(nickname4);
        dto.setNickname5(nickname5);


        // 테스트
        user.updateUser(dto);


        // 확인
        assertThat(user.getNickname1(), is(equalTo(nickname1)));
        assertThat(user.getNickname2(), is(equalTo(nickname2)));
        assertThat(user.getNickname3(), is(equalTo(nickname3)));
        assertThat(user.getNickname4(), is(equalTo(nickname4)));
        assertThat(user.getNickname5(), is(equalTo(nickname5)));
        assertThat(user.getFriendCode1(), is(equalTo(friendCode1)));
        assertThat(user.getFriendCode2(), is(equalTo(friendCode2)));
        assertThat(user.getFriendCode3(), is(equalTo(friendCode3)));
        assertThat(user.getFriendCode4(), is(equalTo(friendCode4)));
        assertThat(user.getFriendCode5(), is(equalTo(friendCode5)));
        assertThat(user.getUserType(), is(equalTo(UserType.ROLE_NOTUSER)));
        assertThat(user.getUserState(), is(equalTo(UserState.FREE)));
        assertThat(user.getEmail(), is(equalTo(email)));
        assertThat(user.getPassword(), is(equalTo(password)));
    }

    @Test
    void upLikeCount_테스트() {

        // 준비
        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();


        // 테스트
        user.upLikeCount();


        // 확인
        assertThat(user.getLikeCount(), is(equalTo(1)));
    }

    @Test
    void upDislikeCount_테스트() {

        // 준비
        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();


        // 테스트
        user.upDislikeCount();


        // 확인
        assertThat(user.getDislikeCount(), is(equalTo(1)));
    }
}
