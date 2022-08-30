package com.pokewith.raid;

import com.pokewith.raid.dto.RaidDto;
import com.pokewith.raid.dto.RpRaidListDto;
import com.pokewith.raid.dto.RqRaidListSearchDto;
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
    private final EntityManager em;

    @Override
    public ResponseEntity<RpRaidListDto> getRaidList(RqRaidListSearchDto dto, Pageable pageable) {
        Page<Raid> raids = raidQueryRepository.raidList(dto, pageable);

        List<RaidDto> raidDtoList = raids.stream()
                .map(RaidDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new RpRaidListDto(raidDtoList, raids.getTotalPages(), raids.getTotalElements()), HttpStatus.OK);
    }
}
