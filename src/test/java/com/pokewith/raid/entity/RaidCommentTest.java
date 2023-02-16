package com.pokewith.raid.entity;

import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidComment;
import com.pokewith.raid.RaidCommentState;
import com.pokewith.raid.RaidType;
import com.pokewith.raid.dto.request.RqPostRaidCommentDto;
import com.pokewith.raid.dto.request.RqPostRaidDto;
import com.pokewith.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RaidCommentTest {

    private final String email = "abc@abc.com";
    private final String password = "1111";
    private final String nickname = "tester";
    private final String friendCode = "0000-0000-0000-0000";

    private final String pokemon = "150";
    private final RaidType raidType = RaidType.FIVE;
    private final LocalDateTime startTime = LocalDateTime.now();
    private final LocalDateTime endTime = LocalDateTime.now();
    private final int normalPass = 1;
    private final int remotePass = 0;

    private final User user = User.NormalSignUpBuilder()
            .email(email)
            .password(password)
            .nickname1(nickname)
            .friendCode1(friendCode)
            .build();

    @Test
    void RaidComment_Builder_테스트() {

        // 준비
        RqPostRaidDto rqPostRaidDto = new RqPostRaidDto();
        rqPostRaidDto.setPokemon(pokemon);
        rqPostRaidDto.setRaidType(raidType);
        rqPostRaidDto.setStartTime(startTime);
        rqPostRaidDto.setEndTime(endTime);
        rqPostRaidDto.setNormalPass(normalPass);
        rqPostRaidDto.setRemotePass(remotePass);

        Raid raid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(user)
                .build();

        boolean account1 = true;
        boolean account2 = false;
        boolean account3 = true;
        boolean account4 = false;
        boolean account5 = false;

        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(account1);
        rqPostRaidCommentDto.setAccount2(account2);
        rqPostRaidCommentDto.setAccount3(account3);
        rqPostRaidCommentDto.setAccount4(account4);
        rqPostRaidCommentDto.setAccount5(account5);

        // 테스트
        RaidComment raidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(user)
                .raid(raid)
                .build();

        // 확인
        assertThat(raidComment.isAccount1(),  is(equalTo(account1)));
        assertThat(raidComment.isAccount2(),  is(equalTo(account2)));
        assertThat(raidComment.isAccount3(),  is(equalTo(account3)));
        assertThat(raidComment.isAccount4(),  is(equalTo(account4)));
        assertThat(raidComment.isAccount5(),  is(equalTo(account5)));
        assertThat(raidComment.getRaidCommentState(), is(equalTo(RaidCommentState.WAITING)));
        assertThat(raidComment.getRaid(), is(equalTo(raid)));
        assertThat(raidComment.getUser(), is(equalTo(user)));

    }

    @Test
    void joinedComment_테스트() {

        // 준비
        RqPostRaidDto rqPostRaidDto = new RqPostRaidDto();
        rqPostRaidDto.setPokemon(pokemon);
        rqPostRaidDto.setRaidType(raidType);
        rqPostRaidDto.setStartTime(startTime);
        rqPostRaidDto.setEndTime(endTime);
        rqPostRaidDto.setNormalPass(normalPass);
        rqPostRaidDto.setRemotePass(remotePass);

        Raid raid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(user)
                .build();

        boolean account1 = true;
        boolean account2 = false;
        boolean account3 = true;
        boolean account4 = false;
        boolean account5 = false;

        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(account1);
        rqPostRaidCommentDto.setAccount2(account2);
        rqPostRaidCommentDto.setAccount3(account3);
        rqPostRaidCommentDto.setAccount4(account4);
        rqPostRaidCommentDto.setAccount5(account5);

        RaidComment raidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(user)
                .raid(raid)
                .build();

        // 테스트
        raidComment.joinedComment();

        // 확인
        assertThat(raidComment.getRaidCommentState(), is(equalTo(RaidCommentState.JOINED)));

    }

    @Test
    void rejectedComment_테스트() {

        // 준비
        RqPostRaidDto rqPostRaidDto = new RqPostRaidDto();
        rqPostRaidDto.setPokemon(pokemon);
        rqPostRaidDto.setRaidType(raidType);
        rqPostRaidDto.setStartTime(startTime);
        rqPostRaidDto.setEndTime(endTime);
        rqPostRaidDto.setNormalPass(normalPass);
        rqPostRaidDto.setRemotePass(remotePass);

        Raid raid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(user)
                .build();

        boolean account1 = true;
        boolean account2 = false;
        boolean account3 = true;
        boolean account4 = false;
        boolean account5 = false;

        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(account1);
        rqPostRaidCommentDto.setAccount2(account2);
        rqPostRaidCommentDto.setAccount3(account3);
        rqPostRaidCommentDto.setAccount4(account4);
        rqPostRaidCommentDto.setAccount5(account5);

        RaidComment raidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(user)
                .raid(raid)
                .build();

        // 테스트
        raidComment.rejectedComment();

        // 확인
        assertThat(raidComment.getRaidCommentState(), is(equalTo(RaidCommentState.REJECTED)));

    }

    @Test
    void voteComment_테스트() {

        // 준비
        RqPostRaidDto rqPostRaidDto = new RqPostRaidDto();
        rqPostRaidDto.setPokemon(pokemon);
        rqPostRaidDto.setRaidType(raidType);
        rqPostRaidDto.setStartTime(startTime);
        rqPostRaidDto.setEndTime(endTime);
        rqPostRaidDto.setNormalPass(normalPass);
        rqPostRaidDto.setRemotePass(remotePass);

        Raid raid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(user)
                .build();

        boolean account1 = true;
        boolean account2 = false;
        boolean account3 = true;
        boolean account4 = false;
        boolean account5 = false;

        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(account1);
        rqPostRaidCommentDto.setAccount2(account2);
        rqPostRaidCommentDto.setAccount3(account3);
        rqPostRaidCommentDto.setAccount4(account4);
        rqPostRaidCommentDto.setAccount5(account5);

        RaidComment raidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(user)
                .raid(raid)
                .build();

        // 테스트
        raidComment.voteComment();

        // 확인
        assertThat(raidComment.getRaidCommentState(), is(equalTo(RaidCommentState.VOTE)));

    }

    @Test
    void endComment_테스트() {

        // 준비
        RqPostRaidDto rqPostRaidDto = new RqPostRaidDto();
        rqPostRaidDto.setPokemon(pokemon);
        rqPostRaidDto.setRaidType(raidType);
        rqPostRaidDto.setStartTime(startTime);
        rqPostRaidDto.setEndTime(endTime);
        rqPostRaidDto.setNormalPass(normalPass);
        rqPostRaidDto.setRemotePass(remotePass);

        Raid raid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(user)
                .build();

        boolean account1 = true;
        boolean account2 = false;
        boolean account3 = true;
        boolean account4 = false;
        boolean account5 = false;

        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(account1);
        rqPostRaidCommentDto.setAccount2(account2);
        rqPostRaidCommentDto.setAccount3(account3);
        rqPostRaidCommentDto.setAccount4(account4);
        rqPostRaidCommentDto.setAccount5(account5);

        RaidComment raidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(user)
                .raid(raid)
                .build();

        // 테스트
        raidComment.endComment();

        // 확인
        assertThat(raidComment.getRaidCommentState(), is(equalTo(RaidCommentState.END)));

    }
}
