package com.pokewith.mypost;

import com.pokewith.exception.NotFoundException;
import com.pokewith.mypost.dto.RpGetMyPostDto;
import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidQueryRepository;
import com.pokewith.raid.dto.RaidDto;
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
    private final EntityManager em;


    @Override
    public ResponseEntity<RpGetMyPostDto> getMyPost(Long userId) {
        Raid raid = raidQueryRepository.getLastInviteRaidByUserId(userId)
                .orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(RpGetMyPostDto.builder()
                .raidDto(new RaidDto(raid))
                .build(), HttpStatus.OK);
    }

}
