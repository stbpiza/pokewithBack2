package com.pokewith.raid.entity;

import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidState;
import com.pokewith.raid.RaidType;
import com.pokewith.raid.dto.request.RqPostRaidDto;
import com.pokewith.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RaidTest {

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
    void Raid_Builder_테스트() {

        // 준비
        RqPostRaidDto rqPostRaidDto = new RqPostRaidDto();
        rqPostRaidDto.setPokemon(pokemon);
        rqPostRaidDto.setRaidType(raidType);
        rqPostRaidDto.setStartTime(startTime);
        rqPostRaidDto.setEndTime(endTime);
        rqPostRaidDto.setNormalPass(normalPass);
        rqPostRaidDto.setRemotePass(remotePass);


        // 테스트
        Raid raid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(user)
                .build();


        // 확인
        assertThat(raid.getPokemon(), is(equalTo(pokemon)));
        assertThat(raid.getRaidType(), is(equalTo(raidType)));
        assertThat(raid.getStartTime(), is(equalTo(startTime)));
        assertThat(raid.getEndTime(), is(equalTo(endTime)));
        assertThat(raid.getNormalPass(), is(equalTo(normalPass)));
        assertThat(raid.getRemotePass(), is(equalTo(remotePass)));
        assertThat(raid.getRaidState(), is(equalTo(RaidState.INVITE)));
        assertThat(raid.getRequiredLevel(), is(equalTo(0)));
        assertThat(raid.getUser(), is(equalTo(user)));
    }

    @Test
    void startRaid_테스트() {

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


        // 테스트
        raid.startRaid();


        // 확인
        assertThat(raid.getRaidState(), is(equalTo(RaidState.DOING)));
    }

    @Test
    void endRaid_테스트() {

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


        // 테스트
        raid.endRaid();


        // 확인
        assertThat(raid.getRaidState(), is(equalTo(RaidState.VOTE)));
    }

    @Test
    void finalEndRaid_테스트() {

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


        // 테스트
        raid.finalEndRaid();


        // 확인
        assertThat(raid.getRaidState(), is(equalTo(RaidState.DONE)));
    }

    @Test
    void makeChat_테스트() {

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

        String chat = "abcdeabcdeabcdeabcde";


        // 테스트
        raid.makeChat(chat);


        // 확인
        assertThat(raid.getChat(), is(equalTo(chat)));
    }
}
