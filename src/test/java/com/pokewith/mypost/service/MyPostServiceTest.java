package com.pokewith.mypost.service;

import com.pokewith.exception.BadRequestException;
import com.pokewith.exception.ForbiddenException;
import com.pokewith.exception.NotFoundException;
import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidComment;
import com.pokewith.raid.RaidCommentState;
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
import java.util.ArrayList;
import java.util.List;

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

    private final Long INVITE_RAID_WRITER_ID = 1L;
    private final Long INVITE_RAID_COMMENT_WRITER_ID_1 = 2L;
    private final Long DOING_RAID_WRITER_ID = 3L;
    private final Long DONE_RAID_WRITER_ID = 4L;
    private final Long INVITE_RAID_COMMENT_WRITER_ID_4 = 5L;
    private final Long FREE_USER_ID = 6L;
    private final Long INVITE_RAID_COMMENT_WRITER_ID_6 = 7L;
    private final Long INVITE_RAID_COMMENT_WRITER_ID_7 = 8L;
    private final Long INVITE_RAID_COMMENT_WRITER_ID_8 = 9L;
    private final Long INVITE_RAID_COMMENT_WRITER_ID_9 = 10L;

    private final Long INVITE_RAID_ID = 1L;
    private final Long DOING_RAID_ID = 2L;
    private final Long DONE_RAID_ID = 3L;

    private final Long INVITE_RAID_COMMENT_ID = 1L;

    @BeforeAll
    void before() {


        // 유저 세팅
        String email = "abc@abc.com";
        String password = "1111";
        String nickname = "tester";
        String friendCode = "0000-0000-0000-0000";

        for (int i=1; i<=10; i++) {
            User user = User.NormalSignUpBuilder()
                    .email(email+i)
                    .password(password)
                    .nickname1(nickname+i)
                    .friendCode1(friendCode)
                    .build();

            if (i == INVITE_RAID_WRITER_ID || i == DOING_RAID_WRITER_ID) {
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

        Raid inviteRaid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(new User(INVITE_RAID_WRITER_ID))
                .build();

        raidRepository.save(inviteRaid);

        Raid doingRaid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(new User(DOING_RAID_WRITER_ID))
                .build();

        doingRaid.startRaid();
        raidRepository.save(doingRaid);

        Raid doneRaid = Raid.builder()
                .dto(rqPostRaidDto)
                .user(new User(DONE_RAID_WRITER_ID))
                .build();

        doneRaid.finalEndRaid();
        raidRepository.save(doneRaid);


        // 댓글 세팅
        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(true);
        rqPostRaidCommentDto.setAccount2(false);
        rqPostRaidCommentDto.setAccount3(false);
        rqPostRaidCommentDto.setAccount4(false);
        rqPostRaidCommentDto.setAccount5(false);

        for (int i=2; i<=10; i++) {
            if (i == INVITE_RAID_COMMENT_WRITER_ID_1) {
                RaidComment raidComment = RaidComment.builder()
                        .dto(rqPostRaidCommentDto)
                        .raid(inviteRaid)
                        .user(new User((long) i))
                        .build();

                raidCommentRepository.save(raidComment);
            }
        }
    }

    @Test
    void getMyPostRaid_테스트_POST() {

        // 준비
        User member = userRepository.findById(INVITE_RAID_WRITER_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트
        Raid raid = myPostService.getMyPostRaid(member);

        // 확인
        assertThat(raid.getRaidId(), is(equalTo(INVITE_RAID_ID)));
    }

    @Test
    void getMyPostRaid_테스트_COMMENT() {

        // 준비
        User member = userRepository.findById(INVITE_RAID_COMMENT_WRITER_ID_1)
                .orElseThrow(NotFoundException::new);

        // 테스트
        Raid raid = myPostService.getMyPostRaid(member);

        // 확인
        assertThat(raid.getRaidId(), is(equalTo(INVITE_RAID_COMMENT_ID)));
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

    @Test
    void checkVote_테스트_POSTAndVOTE() {

        // 준비
        String email = "vote1@abc.com";
        String password = "1111";
        String nickname = "vote1";
        String friendCode = "0000-0000-0000-0000";

        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        user.setPostState();

        em.persist(user);

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
                .user(user)
                .build();

        raid.endRaid();

        em.persist(raid);

        em.flush();



        // 테스트
        boolean result = myPostService.checkVote(user, raid);

        // 확인
        assertThat(result, is(equalTo(true)));
    }

    @Test
    void checkVote_테스트_POSTAndNotVOTE() {

        // 준비
        String email = "vote2@abc.com";
        String password = "1111";
        String nickname = "vote2";
        String friendCode = "0000-0000-0000-0000";

        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        user.setPostState();

        em.persist(user);

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
                .user(user)
                .build();

        raid.startRaid();

        em.persist(raid);

        em.flush();



        // 테스트
        boolean result = myPostService.checkVote(user, raid);

        // 확인
        assertThat(result, is(equalTo(false)));
    }

    @Test
    void checkVote_테스트_COMMENTAndVOTE() {

        // 준비
        String email = "vote@abc.com";
        String password = "1111";
        String nickname = "vote";
        String friendCode = "0000-0000-0000-0000";

        User writer = User.NormalSignUpBuilder()
                .email(email+3)
                .password(password)
                .nickname1(nickname+3)
                .friendCode1(friendCode)
                .build();

        writer.setPostState();

        User commenter = User.NormalSignUpBuilder()
                .email(email+4)
                .password(password)
                .nickname1(nickname+4)
                .friendCode1(friendCode)
                .build();

        commenter.setCommentState();

        em.persist(writer);
        em.persist(commenter);

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
                .user(writer)
                .build();

        raid.endRaid();

        em.persist(raid);

        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(true);
        rqPostRaidCommentDto.setAccount2(false);
        rqPostRaidCommentDto.setAccount3(false);
        rqPostRaidCommentDto.setAccount4(false);
        rqPostRaidCommentDto.setAccount5(false);

        RaidComment raidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(commenter)
                .raid(raid)
                .build();

        raidComment.voteComment();

        em.persist(raidComment);

        em.flush();
        em.clear();

        Raid myRaid = raidRepository.findById(raid.getRaidId())
                .orElseThrow(NotFoundException::new);


        // 테스트
        boolean result = myPostService.checkVote(commenter, myRaid);

        // 확인
        assertThat(result, is(equalTo(true)));
    }

    @Test
    void checkVote_테스트_COMMENTAndNotVOTE() {

        // 준비
        String email = "vote@abc.com";
        String password = "1111";
        String nickname = "vote";
        String friendCode = "0000-0000-0000-0000";

        User writer = User.NormalSignUpBuilder()
                .email(email+5)
                .password(password)
                .nickname1(nickname+5)
                .friendCode1(friendCode)
                .build();

        writer.setPostState();

        User commenter = User.NormalSignUpBuilder()
                .email(email+6)
                .password(password)
                .nickname1(nickname+6)
                .friendCode1(friendCode)
                .build();

        commenter.setCommentState();

        em.persist(writer);
        em.persist(commenter);

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
                .user(writer)
                .build();

        raid.endRaid();

        em.persist(raid);

        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(true);
        rqPostRaidCommentDto.setAccount2(false);
        rqPostRaidCommentDto.setAccount3(false);
        rqPostRaidCommentDto.setAccount4(false);
        rqPostRaidCommentDto.setAccount5(false);

        RaidComment raidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(commenter)
                .raid(raid)
                .build();

        em.persist(raidComment);

        em.flush();
        em.clear();

        Raid myRaid = raidRepository.findById(raid.getRaidId())
                .orElseThrow(NotFoundException::new);


        // 테스트
        boolean result = myPostService.checkVote(commenter, myRaid);

        // 확인
        assertThat(result, is(equalTo(false)));
    }


    @Test
    void checkWriter_테스트_writer() {

        // 준비
        Raid raid = raidRepository.findById(INVITE_RAID_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.checkWriter(raid, INVITE_RAID_WRITER_ID);

        // 확인
        // 예외발생 없으면 성공
    }

    @Test
    void checkWriter_테스트_notWriter() {

        // 준비
        Raid raid = raidRepository.findById(INVITE_RAID_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트 & 확인
        assertThrows(ForbiddenException.class, () ->
            myPostService.checkWriter(raid, FREE_USER_ID)
        );

    }

    @Test
    void checkRaidStateInvite_테스트_INVITE() {

        // 준비
        Raid raid = raidRepository.findById(INVITE_RAID_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.checkRaidStateInvite(raid);

        // 확인
        // 예외발생 없으면 성공
    }

    @Test
    void checkRaidStateInvite_테스트_NotINVITE() {

        // 준비
        Raid raid = raidRepository.findById(DOING_RAID_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트 & 확인
        assertThrows(BadRequestException.class, () ->
            myPostService.checkRaidStateInvite(raid)
        );

    }

    @Test
    void checkRaidStateDone_테스트_DONE() {

        // 준비
        Raid raid = raidRepository.findById(DONE_RAID_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트 & 확인
        assertThrows(BadRequestException.class, () ->
            myPostService.checkRaidStateDone(raid)
        );

    }

    @Test
    void checkRaidStateDone_테스트_NotDONE() {

        // 준비
        Raid raid = raidRepository.findById(DOING_RAID_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트 & 확인
        myPostService.checkRaidStateDone(raid);

        // 확인
        // 예외발생 없으면 성공

    }

    @Test
    void setCommentStateAllJoinedOrRejected_테스트() {

        // 준비
        String email = "commentstate@abc.com";
        String password = "1111";
        String nickname = "commentstate";
        String friendCode = "0000-0000-0000-0000";

        User writer = User.NormalSignUpBuilder()
                .email(email+1)
                .password(password)
                .nickname1(nickname+1)
                .friendCode1(friendCode)
                .build();

        writer.setPostState();

        User joinedCommenter = User.NormalSignUpBuilder()
                .email(email+2)
                .password(password)
                .nickname1(nickname+2)
                .friendCode1(friendCode)
                .build();

        joinedCommenter.setCommentState();

        User rejectedCommenter = User.NormalSignUpBuilder()
                .email(email+3)
                .password(password)
                .nickname1(nickname+3)
                .friendCode1(friendCode)
                .build();

        rejectedCommenter.setCommentState();

        User alreadyRejectedCommenter = User.NormalSignUpBuilder()
                .email(email+4)
                .password(password)
                .nickname1(nickname+4)
                .friendCode1(friendCode)
                .build();

        alreadyRejectedCommenter.setFreeState();

        em.persist(writer);
        em.persist(joinedCommenter);
        em.persist(rejectedCommenter);
        em.persist(alreadyRejectedCommenter);

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
                .user(writer)
                .build();

        em.persist(raid);

        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(true);
        rqPostRaidCommentDto.setAccount2(false);
        rqPostRaidCommentDto.setAccount3(false);
        rqPostRaidCommentDto.setAccount4(false);
        rqPostRaidCommentDto.setAccount5(false);

        RaidComment joinedRaidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(joinedCommenter)
                .raid(raid)
                .build();

        RaidComment rejectedRaidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(rejectedCommenter)
                .raid(raid)
                .build();

        RaidComment alreadyRejectedRaidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(alreadyRejectedCommenter)
                .raid(raid)
                .build();

        em.persist(joinedRaidComment);
        em.persist(rejectedRaidComment);
        em.persist(alreadyRejectedRaidComment);

        em.flush();
        em.clear();

        Raid raidAndComment = raidRepository.findById(raid.getRaidId())
                .orElseThrow(NotFoundException::new);

        List<RaidComment> raidCommentList = raidAndComment.getRaidComments();

        List<Long> raidCommentIdList = new ArrayList<>();
        raidCommentIdList.add(joinedRaidComment.getRaidCommentId());

        // 테스트
        myPostService.setCommentStateAllJoinedOrRejected(raidCommentList, raidCommentIdList);

        // 확인
        assertThat(raidCommentList.get(0).getRaidCommentState(), is(equalTo(RaidCommentState.JOINED)));
        assertThat(raidCommentList.get(1).getRaidCommentState(), is(equalTo(RaidCommentState.REJECTED)));
        assertThat(raidCommentList.get(2).getRaidCommentState(), is(equalTo(RaidCommentState.REJECTED)));
    }
}
