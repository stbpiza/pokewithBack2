package com.pokewith.raid.service;

import com.pokewith.exception.BadRequestException;
import com.pokewith.exception.ConflictException;
import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidState;
import com.pokewith.raid.RaidType;
import com.pokewith.raid.dto.request.RqPostRaidDto;
import com.pokewith.raid.dto.request.RqRaidListSearchDto;
import com.pokewith.raid.dto.response.RpRaidListDto;
import com.pokewith.raid.repository.RaidRepository;
import com.pokewith.user.User;
import com.pokewith.user.UserState;
import com.pokewith.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RaidServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    RaidServiceImpl raidService;

    @Autowired
    RaidRepository raidRepository;

    @Autowired
    UserRepository userRepository;

    private final int MEGA_COUNT = 5;
    private final int FIVE_COUNT = 10;
    private final int THREE_COUNT = 6;
    private final int ONE_COUNT = 2;

    private final Pageable pageable = new Pageable() {
        @Override
        public int getPageNumber() {
            return 0;
        }

        @Override
        public int getPageSize() {
            return 50;
        }

        @Override
        public long getOffset() {
            return 0;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public Pageable next() {
            return null;
        }

        @Override
        public Pageable previousOrFirst() {
            return null;
        }

        @Override
        public Pageable first() {
            return null;
        }

        @Override
        public Pageable withPage(int pageNumber) {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }
    };

    @BeforeAll
    void before() {

        // 유저 세팅
        String email = "abc@abc.com";
        String password = "1111";
        String nickname = "tester";
        String friendCode = "0000-0000-0000-0000";

        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        userRepository.save(user);

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

        for (int i=0; i<MEGA_COUNT; i++) {
            rqPostRaidDto.setRaidType(RaidType.MEGA);

            Raid raid = Raid.builder()
                    .dto(rqPostRaidDto)
                    .user(user)
                    .build();

            raidRepository.save(raid);
        }

        for (int i=0; i<FIVE_COUNT; i++) {
            rqPostRaidDto.setRaidType(RaidType.FIVE);

            Raid raid = Raid.builder()
                    .dto(rqPostRaidDto)
                    .user(user)
                    .build();

            raidRepository.save(raid);
        }

        for (int i=0; i<THREE_COUNT; i++) {
            rqPostRaidDto.setRaidType(RaidType.THREE);

            Raid raid = Raid.builder()
                    .dto(rqPostRaidDto)
                    .user(user)
                    .build();

            raidRepository.save(raid);
        }

        for (int i=0; i<ONE_COUNT; i++) {
            rqPostRaidDto.setRaidType(RaidType.ONE);

            Raid raid = Raid.builder()
                    .dto(rqPostRaidDto)
                    .user(user)
                    .build();

            raidRepository.save(raid);
        }

    }

    @Test
    void 레이드리스트조회테스트_MEGA() {

        // 준비
        RqRaidListSearchDto dto = new RqRaidListSearchDto();
        dto.setState(RaidState.INVITE);
        dto.setType(RaidType.MEGA);

        // 테스트
        ResponseEntity<RpRaidListDto> raidList = raidService.getRaidList(dto, pageable);

        // 확인
        assertThat(raidList.getBody().getTotalCount().intValue(), is(equalTo(MEGA_COUNT)));
    }

    @Test
    void 레이드리스트조회테스트_FIVE() {

        // 준비
        RqRaidListSearchDto dto = new RqRaidListSearchDto();
        dto.setState(RaidState.INVITE);
        dto.setType(RaidType.FIVE);

        // 테스트
        ResponseEntity<RpRaidListDto> raidList = raidService.getRaidList(dto, pageable);

        // 확인
        assertThat(raidList.getBody().getTotalCount().intValue(), is(equalTo(FIVE_COUNT)));
    }

    @Test
    void 레이드리스트조회테스트_THREE() {

        // 준비
        RqRaidListSearchDto dto = new RqRaidListSearchDto();
        dto.setState(RaidState.INVITE);
        dto.setType(RaidType.THREE);

        // 테스트
        ResponseEntity<RpRaidListDto> raidList = raidService.getRaidList(dto, pageable);

        // 확인
        assertThat(raidList.getBody().getTotalCount().intValue(), is(equalTo(THREE_COUNT)));
    }

    @Test
    void 레이드리스트조회테스트_ONE() {

        // 준비
        RqRaidListSearchDto dto = new RqRaidListSearchDto();
        dto.setState(RaidState.INVITE);
        dto.setType(RaidType.ONE);

        // 테스트
        ResponseEntity<RpRaidListDto> raidList = raidService.getRaidList(dto, pageable);

        // 확인
        assertThat(raidList.getBody().getTotalCount().intValue(), is(equalTo(ONE_COUNT)));
    }

    @Test
    void getUserAndCheckUserState_테스트_FREEState() {

        // 준비
        String email = "abcd@abc.com";
        String password = "1111";
        String nickname = "tester2";
        String friendCode = "0000-0000-0000-0000";

        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        em.persist(user);
        em.flush();

        // 테스트
        User member = raidService.getUserAndCheckUserState(user.getUserId());

        // 확인
        assertThat(member.getUserState(), is(equalTo(UserState.FREE)));
    }

    @Test
    void getUserAndCheckUserState_테스트_NotFREEState() {

        // 준비
        String email = "abcd@abc.com";
        String password = "1111";
        String nickname = "tester2";
        String friendCode = "0000-0000-0000-0000";

        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

        user.setPostState();

        em.persist(user);
        em.flush();

        // 테스트 & 확인
        assertThrows(ConflictException.class, () ->
                raidService.getUserAndCheckUserState(user.getUserId())
        );
    }

    @Test
    void getRaidAndCheckRaidState_테스트_INVITEState() {

        // 준비
        String email = "abcd@abc.com";
        String password = "1111";
        String nickname = "tester2";
        String friendCode = "0000-0000-0000-0000";

        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

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

        em.persist(raid);
        em.flush();

        // 테스트
        Raid raidResult = raidService.getRaidAndCheckRaidState(raid.getRaidId());

        // 확인
        assertThat(raidResult.getRaidState(), is(equalTo(RaidState.INVITE)));
    }

    @Test
    void getRaidAndCheckRaidState_테스트_NotINVITEState() {

        // 준비
        String email = "abcd@abc.com";
        String password = "1111";
        String nickname = "tester2";
        String friendCode = "0000-0000-0000-0000";

        User user = User.NormalSignUpBuilder()
                .email(email)
                .password(password)
                .nickname1(nickname)
                .friendCode1(friendCode)
                .build();

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

        // 테스트 & 확인
        assertThrows(BadRequestException.class, () ->
                raidService.getRaidAndCheckRaidState(raid.getRaidId())
        );
    }

}
