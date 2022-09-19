package com.pokewith.mypost;

import com.pokewith.auth.UsernameService;
import com.pokewith.mypost.dto.RpGetMyPostDto;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPostController {

    private final UsernameService usernameService;
    private final MyPostService myPostService;

    @GetMapping("/mypost")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마이포스트 조회 성공"),
            @ApiResponse(code = 404, message = "작성한 글이나 댓글 없음")
    })
    public ResponseEntity<RpGetMyPostDto> getMyPost(HttpServletRequest request) {
        log.info("/api/mypost");
        Long userId = usernameService.getUsername(request);
        return myPostService.getMyPost(userId);
    }
}
