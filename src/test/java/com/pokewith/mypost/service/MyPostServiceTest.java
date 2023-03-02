package com.pokewith.mypost.service;

import com.pokewith.exception.NotFoundException;
import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidComment;
import com.pokewith.raid.RaidType;
import com.pokewith.raid.dto.request.RqPostRaidCommentDto;
import com.pokewith.raid.dto.request.RqPostRaidDto;
import com.pokewith.raid.repository.RaidCommentRepository;
import com.pokewith.raid.repository.RaidRepository;
import com.pokewith.user.User;
import com.pokewith.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyPostServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    RaidRepository raidRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RaidCommentRepository raidCommentRepository;

    @Autowired
    MyPostServiceImpl myPostService;

    private final Long RAID_WRITER_ID = 1L;
    private final Long RAID_COMMENT_WRITER_ID_1 = 2L;
    private final Long RAID_COMMENT_WRITER_ID_2 = 3L;
    private final Long RAID_COMMENT_WRITER_ID_3 = 4L;
    private final Long RAID_COMMENT_WRITER_ID_4 = 5L;
    private final Long FREE_USER_ID = 6L;
    private final Long TEST_RAID_ID = 1L;
    private final Long TEST_RAID_COMMENT_ID = 1L;

    @BeforeAll
    void before() {


        // 유저 세팅
        String email = "abc@abc.com";
        String password = "1111";
        String nickname = "tester";
        String friendCode = "0000-0000-0000-0000";

        for (int i=1; i<=6; i++) {
            User user = User.NormalSignUpBuilder()
                    .email(email+i)
                    .password(password)
                    .nickname1(nickname+i)
                    .friendCode1(friendCode)
                    .build();

            if (i == RAID_WRITER_ID) {
                user.setPostState();
            } else if (i == FREE_USER_ID) {
                user.setFreeState();
            } else {
                user.setCommentState();
            }

            userRepository.save(user);
        }

        // 레이드 세팅
        String pokemon = "150";
        RaidType raidType = RaidType.FIVE;
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now();
        int normalPass = 1;
        int remotePass = 0;

        RqPostRaidDto rqPostRaidDto = new RqPostRaidDto();
        rqPostRaidDto.setPokemon(pokemon);
        rqPostRaidDto.setRaidType(raidType);
        rqPostRaidDto.setStartTime(startTime);
        rqPostRaidDto.setEndTime(endTime);
        rqPostRaidDto.setNormalPass(normalPass);
        rqPostRaidDto.setRemotePass(remotePass);

        Raid raid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(new User(RAID_WRITER_ID))
                .build();

        raidRepository.save(raid);


        // 댓글 세팅
        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setRaidId(TEST_RAID_ID);
        rqPostRaidCommentDto.setAccount1(true);
        rqPostRaidCommentDto.setAccount2(false);
        rqPostRaidCommentDto.setAccount3(false);
        rqPostRaidCommentDto.setAccount4(false);
        rqPostRaidCommentDto.setAccount5(false);

        for (int i=2; i<=5; i++) {
            RaidComment raidComment = RaidComment.builder()
                    .dto(rqPostRaidCommentDto)
                    .raid(raid)
                    .user(new User((long) i))
                    .build();

            raidCommentRepository.save(raidComment);
        }
    }

    @Test
    void getMyPostRaid_테스트_POST() {

        // 준비
        User member = userRepository.findById(RAID_WRITER_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트
        Raid raid = myPostService.getMyPostRaid(member);

        // 확인
        assertThat(raid.getRaidId(), is(equalTo(TEST_RAID_ID)));
    }

    @Test
    void getMyPostRaid_테스트_COMMENT() {

        // 준비
        User member = userRepository.findById(RAID_COMMENT_WRITER_ID_1)
                .orElseThrow(NotFoundException::new);

        // 테스트
        Raid raid = myPostService.getMyPostRaid(member);

        // 확인
        assertThat(raid.getRaidId(), is(equalTo(TEST_RAID_COMMENT_ID)));
    }

    @Test
    void getMyPostRaid_테스트_FREE() {

        // 준비
        User member = userRepository.findById(FREE_USER_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트
        Raid raid = myPostService.getMyPostRaid(member);

        // 확인
        assertThat(raid, is(equalTo(null)));
    }


}
