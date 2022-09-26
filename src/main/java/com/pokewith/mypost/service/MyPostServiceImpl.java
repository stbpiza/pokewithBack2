package com.pokewith.mypost.service;

import com.pokewith.exception.ForbiddenException;
import com.pokewith.exception.NotFoundException;
import com.pokewith.mypost.dto.RpGetMyPostDto;
import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidComment;
import com.pokewith.raid.repository.RaidCommentQueryRepository;
import com.pokewith.raid.repository.RaidQueryRepository;
import com.pokewith.raid.dto.RaidDto;
import com.pokewith.user.User;
import com.pokewith.user.UserState;
import com.pokewith.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MyPostServiceImpl implements MyPostService{

    private final RaidQueryRepository raidQueryRepository;
    private final RaidCommentQueryRepository raidCommentQueryRepository;
    private final UserRepository userRepository;
    private final EntityManager em;


    @Override
    public ResponseEntity<RpGetMyPostDto> getMyPost(Long userId) {

        User member = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        Raid raid = getMyPostRaid(member);

        return new ResponseEntity<>(RpGetMyPostDto.builder()
                .raidDto(new RaidDto(raid))
                .build(), HttpStatus.OK);
    }


    /**
     *  분리한 메소드
     **/

    private Raid getMyPostRaid(User member) {

        if (member.getUserState().equals(UserState.POST)) {
            return raidQueryRepository.getLastInviteRaidByUserId(member.getUserId())
                    .orElseThrow(NotFoundException::new);
        } else if (member.getUserState().equals(UserState.COMMENT)) {
            RaidComment raidComment = raidCommentQueryRepository.getLastCommentAndRaidByUserId(member.getUserId())
                    .orElseThrow(NotFoundException::new);
            return raidComment.getRaid();
        } else {
            return null;
        }
    }


}
