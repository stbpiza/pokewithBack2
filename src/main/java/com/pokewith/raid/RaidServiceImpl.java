package com.pokewith.raid;

import com.pokewith.exception.auth.DbErrorException;
import com.pokewith.raid.dto.RaidDto;
import com.pokewith.raid.dto.RpRaidListDto;
import com.pokewith.raid.dto.RqPostRaidDto;
import com.pokewith.raid.dto.RqRaidListSearchDto;
import com.pokewith.user.User;
import com.pokewith.user.UserRepository;
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

        User member = userRepository.findById(userId).orElseThrow(DbErrorException::new);

        // 이미 참여중인 레이드가 있는지 확인
        if (member.getUserState() != UserState.FREE) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }

        Raid raid = Raid.builder()
                .dto(dto)
                .user(member)
                .build();

        em.persist(raid);

        // userState 참여중으로 변경
        member.setPostState();

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
