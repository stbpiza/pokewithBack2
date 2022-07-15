package com.pokewith.auth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@Builder
@RedisHash
public class GitToken {
    @Id
    private String id;
    private String accessToken;
    private String refreshToken;
    private String oauth2Nickname;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long timeToLive;


}
