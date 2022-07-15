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
public class EmailToken {
    @Id
    private String id;
    private String random;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long timeToLive;
}
