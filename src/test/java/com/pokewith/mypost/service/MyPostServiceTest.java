package com.pokewith.mypost.service;

import com.pokewith.exception.BadRequestException;
import com.pokewith.exception.ForbiddenException;
import com.pokewith.exception.NotFoundException;
import com.pokewith.mypost.dto.LikeAndDislikeDto;
import com.pokewith.raid.*;
import com.pokewith.raid.dto.request.RqPostRaidCommentDto;
import com.pokewith.raid.dto.request.RqPostRaidDto;
import com.pokewith.raid.repository.RaidCommentRepository;
import com.pokewith.raid.repository.RaidRepository;
import com.pokewith.user.LikeOrDislike;
import com.pokewith.user.User;
import com.pokewith.user.UserState;
import com.pokewith.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    private final Long INVITE_RAID_COMMENT_WRITER_ID_2 = 5L;
    private final Long FREE_USER_ID = 6L;
    private final Long DOING_RAID_COMMENT_WRITER_ID_1 = 7L;
    private final Long VOTE_RAID_WRITER_ID = 8L;
    private final Long VOTE_RAID_COMMENT_WRITER_ID_1 = 9L;
    private final Long VOTE_RAID_COMMENT_WRITER_ID_2 = 10L;

    private final Long INVITE_RAID_ID = 1L;
    private final Long DOING_RAID_ID = 2L;
    private final Long DONE_RAID_ID = 3L;
    private final Long VOTE_RAID_ID = 4L;

    private final Long INVITE_RAID_COMMENT_ID_1 = 1L;
    private final Long INVITE_RAID_COMMENT_ID_2 = 2L;
    private final Long DOING_RAID_COMMENT_ID_1 = 3L;
    private final Long VOTE_RAID_COMMENT_ID_1 = 4L;
    private final Long VOTE_RAID_COMMENT_ID_2 = 5L;

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

            if (i == INVITE_RAID_WRITER_ID || i == DOING_RAID_WRITER_ID
             || i == VOTE_RAID_WRITER_ID) {
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

        Raid doingRaid2 = Raid.builder()
                .dto(rqPostRaidDto)
                .user(new User(VOTE_RAID_WRITER_ID))
                .build();

        doingRaid2.endRaid();
        raidRepository.save(doingRaid2);


        // 댓글 세팅
        RqPostRaidCommentDto rqPostRaidCommentDto = new RqPostRaidCommentDto();
        rqPostRaidCommentDto.setAccount1(true);
        rqPostRaidCommentDto.setAccount2(false);
        rqPostRaidCommentDto.setAccount3(false);
        rqPostRaidCommentDto.setAccount4(false);
        rqPostRaidCommentDto.setAccount5(false);

        for (int i=2; i<=10; i++) {
            if (i == INVITE_RAID_COMMENT_WRITER_ID_1 || i == INVITE_RAID_COMMENT_WRITER_ID_2) {
                RaidComment raidComment = RaidComment.builder()
                        .dto(rqPostRaidCommentDto)
                        .raid(inviteRaid)
                        .user(new User((long) i))
                        .build();

                raidCommentRepository.save(raidComment);
            } else if (i == DOING_RAID_COMMENT_WRITER_ID_1) {
                RaidComment raidComment = RaidComment.builder()
                        .dto(rqPostRaidCommentDto)
                        .raid(doingRaid)
                        .user(new User((long) i))
                        .build();

                raidComment.joinedComment();
                raidCommentRepository.save(raidComment);
            } else if (i == VOTE_RAID_COMMENT_WRITER_ID_1 || i == VOTE_RAID_COMMENT_WRITER_ID_2) {
                RaidComment raidComment = RaidComment.builder()
                        .dto(rqPostRaidCommentDto)
                        .raid(doingRaid2)
                        .user(new User((long) i))
                        .build();

                raidComment.voteComment();
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
        assertThat(raid.getRaidId(), is(equalTo(INVITE_RAID_COMMENT_ID_1)));
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

        alreadyRejectedRaidComment.rejectedComment();

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


    @Test
    void endRaidByRaidState_테스트_INVITE() {
        // 준비
        String email = "endraidtest@abc.com";
        String password = "1111";
        String nickname = "endraid";
        String friendCode = "0000-0000-0000-0000";

        User writer = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        writer.setPostState();

        em.persist(writer);

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

        em.flush();
        em.clear();

        Raid testRaid = raidRepository.findById(raid.getRaidId())
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.endRaidByRaidState(testRaid);

        // 확인
        assertThat(testRaid.getRaidState(), is(equalTo(RaidState.DONE)));
        assertThat(testRaid.getUser().getUserState(), is(equalTo(UserState.FREE)));
    }

    @Test
    void endRaidByRaidState_테스트_DOING() {
        // 준비
        String email = "endraidtest@abc.com";
        String password = "1111";
        String nickname = "endraid";
        String friendCode = "0000-0000-0000-0000";

        User writer = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        writer.setPostState();

        em.persist(writer);

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

        raid.startRaid();

        em.persist(raid);

        em.flush();
        em.clear();

        Raid testRaid = raidRepository.findById(raid.getRaidId())
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.endRaidByRaidState(testRaid);

        // 확인
        assertThat(testRaid.getRaidState(), is(equalTo(RaidState.VOTE)));
        assertThat(testRaid.getUser().getUserState(), is(equalTo(UserState.POST)));
    }

    @Test
    void endRaidByRaidState_테스트_DONE() {
        // 준비
        Raid testRaid = raidRepository.findById(DONE_RAID_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.endRaidByRaidState(testRaid);

        // 확인
        assertThat(testRaid.getRaidState(), is(equalTo(RaidState.DONE)));
    }

    @Test
    void setCommentStateAllEndByRaidState_테스트_INVITE() {

        // 준비
        String email = "commentstateend@abc.com";
        String password = "1111";
        String nickname = "commentstateend";
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

        User alreadyRejectedCommenter = User.NormalSignUpBuilder()
                .email(email+3)
                .password(password)
                .nickname1(nickname+3)
                .friendCode1(friendCode)
                .build();

        alreadyRejectedCommenter.setFreeState();

        em.persist(writer);
        em.persist(joinedCommenter);
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

        RaidComment alreadyRejectedRaidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(alreadyRejectedCommenter)
                .raid(raid)
                .build();

        alreadyRejectedRaidComment.rejectedComment();

        em.persist(joinedRaidComment);
        em.persist(alreadyRejectedRaidComment);

        em.flush();
        em.clear();

        Raid raidAndComment = raidRepository.findById(raid.getRaidId())
                .orElseThrow(NotFoundException::new);

        List<RaidComment> raidCommentList = raidAndComment.getRaidComments();

        // 테스트
        myPostService.setCommentStateAllEndByRaidState(raidCommentList, raidAndComment.getRaidState());

        // 확인
        assertThat(raidCommentList.get(0).getRaidCommentState(), is(equalTo(RaidCommentState.REJECTED)));
        assertThat(raidCommentList.get(1).getRaidCommentState(), is(equalTo(RaidCommentState.REJECTED)));
        assertThat(raidCommentList.get(0).getUser().getUserState(), is(equalTo(UserState.FREE)));
        assertThat(raidCommentList.get(1).getUser().getUserState(), is(equalTo(UserState.FREE)));
    }

    @Test
    void setCommentStateAllEndByRaidState_테스트_NotINVITE() {
        // 준비
        String email = "commentstate@abc.com";
        String password = "1111";
        String nickname = "commentstate";
        String friendCode = "0000-0000-0000-0000";

        User writer = User.NormalSignUpBuilder()
                .email(email+4)
                .password(password)
                .nickname1(nickname+4)
                .friendCode1(friendCode)
                .build();

        writer.setPostState();

        User joinedCommenter = User.NormalSignUpBuilder()
                .email(email+5)
                .password(password)
                .nickname1(nickname+5)
                .friendCode1(friendCode)
                .build();

        joinedCommenter.setCommentState();

        User alreadyRejectedCommenter = User.NormalSignUpBuilder()
                .email(email+6)
                .password(password)
                .nickname1(nickname+6)
                .friendCode1(friendCode)
                .build();

        alreadyRejectedCommenter.setFreeState();

        em.persist(writer);
        em.persist(joinedCommenter);
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

        raid.startRaid();

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

        joinedRaidComment.joinedComment();

        RaidComment alreadyRejectedRaidComment = RaidComment.builder()
                .dto(rqPostRaidCommentDto)
                .user(alreadyRejectedCommenter)
                .raid(raid)
                .build();

        alreadyRejectedRaidComment.rejectedComment();

        em.persist(joinedRaidComment);
        em.persist(alreadyRejectedRaidComment);

        em.flush();
        em.clear();

        Raid raidAndComment = raidRepository.findById(raid.getRaidId())
                .orElseThrow(NotFoundException::new);

        List<RaidComment> raidCommentList = raidAndComment.getRaidComments();

        // 테스트
        myPostService.setCommentStateAllEndByRaidState(raidCommentList, raidAndComment.getRaidState());

        // 확인
        assertThat(raidCommentList.get(0).getRaidCommentState(), is(equalTo(RaidCommentState.VOTE)));
        assertThat(raidCommentList.get(1).getRaidCommentState(), is(equalTo(RaidCommentState.REJECTED)));
        assertThat(raidCommentList.get(0).getUser().getUserState(), is(equalTo(UserState.COMMENT)));
        assertThat(raidCommentList.get(1).getUser().getUserState(), is(equalTo(UserState.FREE)));
    }


    @Test
    void checkCommentState_테스트_VOTE() {

        // 준비
        RaidCommentState raidCommentState = RaidCommentState.VOTE;

        // 테스트 & 확인
        assertThrows(BadRequestException.class, () ->
                myPostService.checkCommentState(raidCommentState)
        );

    }

    @Test
    void checkCommentState_테스트_REJECTED() {

        // 준비
        RaidCommentState raidCommentState = RaidCommentState.REJECTED;

        // 테스트 & 확인
        assertThrows(BadRequestException.class, () ->
                myPostService.checkCommentState(raidCommentState)
        );

    }

    @Test
    void checkCommentState_테스트_END() {

        // 준비
        RaidCommentState raidCommentState = RaidCommentState.END;

        // 테스트 & 확인
        assertThrows(BadRequestException.class, () ->
                myPostService.checkCommentState(raidCommentState)
        );

    }

    @Test
    void checkCommentState_테스트_WAITING() {

        // 준비
        RaidCommentState raidCommentState = RaidCommentState.WAITING;

        // 테스트
        myPostService.checkCommentState(raidCommentState);

        // 확인
        // 예외발생 없으면 성공
    }

    @Test
    void checkCommentState_테스트_JOINED() {

        // 준비
        RaidCommentState raidCommentState = RaidCommentState.JOINED;

        // 테스트
        myPostService.checkCommentState(raidCommentState);

        // 확인
        // 예외발생 없으면 성공
    }

    @Test
    void endCommentByRaidState_테스트_INVITE() {

        // 준비
        RaidComment raidComment = raidCommentRepository.findById(INVITE_RAID_COMMENT_ID_1)
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.endCommentByRaidState(raidComment);

        // 확인
        assertThat(raidComment.getRaidCommentState(), is(equalTo(RaidCommentState.REJECTED)));
        assertThat(raidComment.getUser().getUserState(), is(equalTo(UserState.FREE)));

    }

    @Test
    void endCommentByRaidState_테스트_DOING() {

        // 준비
        RaidComment raidComment = raidCommentRepository.findById(DOING_RAID_COMMENT_ID_1)
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.endCommentByRaidState(raidComment);

        // 확인
        assertThat(raidComment.getRaidCommentState(), is(equalTo(RaidCommentState.VOTE)));
        assertThat(raidComment.getUser().getUserState(), is(equalTo(UserState.COMMENT)));

    }

    @Test
    void checkUserState_테스트_FREE() {

        // 준비
        UserState userState = UserState.FREE;

        // 테스트 & 확인
        assertThrows(BadRequestException.class, () ->
            myPostService.checkUserState(userState)
        );
    }

    @Test
    void checkUserState_테스트_NotFREE() {

        // 준비
        UserState userState = UserState.POST;

        // 테스트
        myPostService.checkUserState(userState);

        // 확인
        // 예외발생 없으면 성공
    }

    @Test
    void insertLikeAndDislike_테스트() {

        // 준비
        List<LikeAndDislikeDto> list = new ArrayList<>();

        LikeAndDislikeDto likeAndDislikeDto1 = new LikeAndDislikeDto();
        likeAndDislikeDto1.setUserId(VOTE_RAID_COMMENT_WRITER_ID_1);
        likeAndDislikeDto1.setLikeOrDislike(LikeOrDislike.LIKE);

        LikeAndDislikeDto likeAndDislikeDto2 = new LikeAndDislikeDto();
        likeAndDislikeDto2.setUserId(VOTE_RAID_COMMENT_WRITER_ID_2);
        likeAndDislikeDto2.setLikeOrDislike(LikeOrDislike.DISLIKE);

        list.add(likeAndDislikeDto1);
        list.add(likeAndDislikeDto2);

        // 테스트
        myPostService.insertLikeAndDislike(list);

        // 확인
        User likeUser = userRepository.findById(VOTE_RAID_COMMENT_WRITER_ID_1)
                .orElseThrow(NotFoundException::new);
        User dislikeUser = userRepository.findById(VOTE_RAID_COMMENT_WRITER_ID_2)
                .orElseThrow(NotFoundException::new);

        assertThat(likeUser.getLikeCount(), is(equalTo(1)));
        assertThat(likeUser.getDislikeCount(), is(equalTo(0)));
        assertThat(dislikeUser.getLikeCount(), is(equalTo(0)));
        assertThat(dislikeUser.getDislikeCount(), is(equalTo(1)));

    }

    @Test
    void endMyRaidOrComment_테스트_POST() {

        // 준비
        User user = userRepository.findById(VOTE_RAID_WRITER_ID)
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.endMyRaidOrComment(user);

        // 확인
        Raid raid = raidRepository.findById(VOTE_RAID_ID)
                .orElseThrow(NotFoundException::new);

        assertThat(raid.getRaidState(), is(equalTo(RaidState.DONE)));
    }

    @Test
    void endMyRaidOrComment_테스트_COMMENT() {

        // 준비
        User user = userRepository.findById(VOTE_RAID_COMMENT_WRITER_ID_1)
                .orElseThrow(NotFoundException::new);

        // 테스트
        myPostService.endMyRaidOrComment(user);

        // 확인
        RaidComment raidComment = raidCommentRepository.findById(VOTE_RAID_COMMENT_ID_1)
                .orElseThrow(NotFoundException::new);

        assertThat(raidComment.getRaidCommentState(), is(equalTo(RaidCommentState.END)));
    }
}
