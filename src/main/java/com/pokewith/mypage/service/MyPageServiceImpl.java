package com.pokewith.mypage.service;

import com.pokewith.exception.BadRequestException;
import com.pokewith.mypage.dto.response.RpGetMyPageDto;
import com.pokewith.mypage.dto.request.RqUpdateMyPageDto;
import com.pokewith.user.User;
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
