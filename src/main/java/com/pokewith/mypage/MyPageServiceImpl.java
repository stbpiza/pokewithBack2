package com.pokewith.mypage;

import com.pokewith.exception.BadRequestException;
import com.pokewith.mypage.dto.RpGetMyPageDto;
import com.pokewith.mypage.dto.RqUpdateMyPageDto;
import com.pokewith.user.User;
import com.pokewith.user.UserRepository;
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
public class MyPageServiceImpl implements MyPageService{

    private final UserRepository userRepository;
    private final EntityManager em;

    @Override
    public ResponseEntity<RpGetMyPageDto> getMyPage(Long userId) {
        User member = userRepository.findById(userId).orElseThrow(BadRequestException::new);

        return new ResponseEntity<>(new RpGetMyPageDto(member), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<String> updateMyPage(Long userId, RqUpdateMyPageDto dto) {
        User member = userRepository.findById(userId).orElseThrow(BadRequestException::new);

        member.updateUser(dto);

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
