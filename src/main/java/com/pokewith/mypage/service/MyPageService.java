package com.pokewith.mypage.service;

import com.pokewith.mypage.dto.RpGetMyPageDto;
import com.pokewith.mypage.dto.RqUpdateMyPageDto;
import org.springframework.http.ResponseEntity;

public interface MyPageService {
    ResponseEntity<RpGetMyPageDto> getMyPage(Long userId);

    ResponseEntity<String> updateMyPage(Long userId, RqUpdateMyPageDto dto);
}
