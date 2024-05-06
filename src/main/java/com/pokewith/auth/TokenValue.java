package com.pokewith.auth;

import lombok.Getter;

public class TokenValue {
    public static final Long tokenValidTime = 24 * 60 * 60 * 1000L;
//    public final Long tokenValidTime = 30 * 60 * 1000L;
    public static final Long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;
    public static final Long emailTokenValidTime = 5 * 60 * 1000L;
    public static final String accessToken = "accessToken";
    public static final String refreshToken = "refreshToken";
    public static final String emailToken = "emailToken";
    public static final String exception = "exception";
    public static final String statusValue = "statusValue";
    public static final String result = "result";
}
