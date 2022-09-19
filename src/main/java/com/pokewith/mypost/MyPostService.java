package com.pokewith.mypost;

import com.pokewith.mypost.dto.RpGetMyPostDto;
import org.springframework.http.ResponseEntity;

public interface MyPostService {
    ResponseEntity<RpGetMyPostDto> getMyPost(Long userId);
}
