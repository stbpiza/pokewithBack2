package com.pokewith.mypost.service;

import com.pokewith.mypost.dto.request.RqPostLikeAndDislikeDto;
import com.pokewith.mypost.dto.request.RqStartRaidDto;
import com.pokewith.mypost.dto.response.RpGetMyPostDto;
import org.springframework.http.ResponseEntity;

public interface MyPostService {
    ResponseEntity<RpGetMyPostDto> getMyPost(Long userId);

    ResponseEntity<String> startRaid(RqStartRaidDto dto, Long userId);

    ResponseEntity<String> endRaid(Long raidId, Long userId);

    ResponseEntity<String> endRaidOneComment(Long userid);

    ResponseEntity<String> postLikeAndDislike(Long userId, RqPostLikeAndDislikeDto dto);
}
