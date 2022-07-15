package com.pokewith.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

@Getter
@NoArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String gitAccessToken;
    private String gitRefreshToken;
    private int result;
    private HttpStatus status;
    private HttpServletResponse response;

    @Builder
    public TokenDto(String accessToken, String refreshToken, String gitAccessToken, String gitRefreshToken, int result, HttpStatus status, HttpServletResponse response) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.gitAccessToken = gitAccessToken;
        this.gitRefreshToken = gitRefreshToken;
        this.result = result;
        this.status = status;
        this.response = response;
    }
}
