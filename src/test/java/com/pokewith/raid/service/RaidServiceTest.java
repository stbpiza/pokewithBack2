package com.pokewith.raid.service;

import com.pokewith.raid.RaidState;
import com.pokewith.raid.RaidType;
import com.pokewith.raid.dto.request.RqRaidListSearchDto;
import com.pokewith.raid.dto.response.RpRaidListDto;
import com.pokewith.raid.repository.RaidRepository;
import com.pokewith.user.User;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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


    @BeforeAll
    void before() {

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

    }

    @Test
    void 레이드리스트조회테스트() {

        // 준비
        RqRaidListSearchDto dto = new RqRaidListSearchDto();
        dto.setState(RaidState.INVITE);
        dto.setType(RaidType.FIVE);

        Pageable pageable = new Pageable() {
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


        //테스트
        ResponseEntity<RpRaidListDto> raidList = raidService.getRaidList(dto, pageable);


        //확인
    }
}
