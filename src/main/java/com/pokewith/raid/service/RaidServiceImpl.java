package com.pokewith.raid.service;

import com.pokewith.exception.BadRequestException;
import com.pokewith.exception.ConflictException;
import com.pokewith.exception.NotFoundException;
import com.pokewith.exception.auth.DbErrorException;
import com.pokewith.raid.*;
import com.pokewith.raid.dto.*;
import com.pokewith.raid.dto.request.RqPostRaidCommentDto;
import com.pokewith.raid.dto.request.RqPostRaidDto;
import com.pokewith.raid.dto.request.RqRaidListSearchDto;
import com.pokewith.raid.dto.response.RpRaidCommentListDto;
import com.pokewith.raid.dto.response.RpRaidListDto;
import com.pokewith.raid.repository.RaidCommentQueryRepository;
import com.pokewith.raid.repository.RaidQueryRepository;
import com.pokewith.raid.repository.RaidRepository;
import com.pokewith.user.User;
import com.pokewith.user.repository.UserRepository;
import com.pokewith.user.UserState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class RaidServiceImpl implements RaidService {

    private final RaidRepository raidRepository;
    private final RaidQueryRepository raidQueryRepository;
    private final UserRepository userRepository;
    private final RaidCommentQueryRepository raidCommentQueryRepository;
    private final EntityManager em;

    @Override
    public ResponseEntity<RpRaidListDto> getRaidList(RqRaidListSearchDto dto, Pageable pageable) {
        Page<Raid> raids = raidQueryRepository.raidList(dto, pageable);

        List<RaidDto> raidDtoList = raids.stream()
                .map(RaidDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new RpRaidListDto(raidDtoList, raids.getTotalPages(), raids.getTotalElements()), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<String> postRaid(RqPostRaidDto dto, Long userId) {

        User member = getUserAndCheckUserState(userId);

        Raid raid = Raid.builder()
                .dto(dto)
                .user(member)
                .build();

        em.persist(raid);

        // userState 참여중으로 변경
        member.setPostState();

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<String> postRaidComment(RqPostRaidCommentDto dto, Long userId) {

        User member = getUserAndCheckUserState(userId);

        Raid raid = getRaidAndCheckRaidState(dto.getRaidId());

        RaidComment raidComment = RaidComment.builder()
                .dto(dto)
                .raid(raid)
                .user(member)
                .build();

        em.persist(raidComment);

        // userState 참여중으로 변경
        member.setCommentState();

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RpRaidCommentListDto> getRaidCommentList(Long raidId) {
        List<RaidComment> raidCommentList = raidCommentQueryRepository.getRaidCommentListByRaidId(raidId);

        return new ResponseEntity<>(RpRaidCommentListDto.builder()
                .raidCommentList(raidCommentList)
                .build(), HttpStatus.OK);
    }

    /**
     *  분리한 메소드
     **/

    private User getUserAndCheckUserState(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(DbErrorException::new);

        // 이미 참여중인 레이드가 있는지 확인
        if (user.getUserState() != UserState.FREE) {
            throw new ConflictException();
        }

        return user;
    }

    private Raid getRaidAndCheckRaidState(Long raidId) {
        Raid raid = raidRepository.findById(raidId).orElseThrow(NotFoundException::new);

        // 모집중 상태인 레이드인지 확인
        if (raid.getRaidState() != RaidState.INVITE) {
            throw new BadRequestException();
        }
        return raid;
    }
}
