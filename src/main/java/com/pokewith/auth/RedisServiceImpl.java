package com.pokewith.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class RedisServiceImpl implements RedisService{

    private final NormalRedisRepository normalRedisRepository;
    private final EmailRedisRepository emailRedisRepository;

    /**
     * 일반 리프레시 토큰
     * **/

    @Override
    public Optional<NormalToken> getNormalData(String username) {
        return normalRedisRepository.findById(username);
    }

    @Override
    public void setNormalData(NormalToken normalToken) { normalRedisRepository.save(normalToken); }

    @Override
    public void deleteNormalData(NormalToken normalToken) { normalRedisRepository.delete(normalToken); }


    /**
     * email 토큰
     * **/

    @Override
    public Optional<EmailToken> getEmailData(String id) {
        return emailRedisRepository.findById(id);
    }

    @Override
    public void setEmailData(EmailToken emailToken) { emailRedisRepository.save(emailToken); }

    @Override
    public void deleteEmailData(EmailToken emailToken) { emailRedisRepository.delete(emailToken); }

}
