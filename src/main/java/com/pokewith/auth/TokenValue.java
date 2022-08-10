package com.pokewith.auth;

import lombok.Getter;

@Getter
public class TokenValue {
    private final Long tokenValidTime = 7 * 24 * 60 * 60 * 1000L;
//    private final Long tokenValidTime = 30 * 60 * 1000L;
    private final Long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;
    private final Long emailTokenValidTime = 5 * 60 * 1000L;
    private final String accessToken = "accessToken";
    private final String refreshToken = "refreshToken";
    private final String emailToken = "emailToken";
    private final String exception = "exception";
    private final String statusValue = "statusValue";
    private final String result = "result";
}
