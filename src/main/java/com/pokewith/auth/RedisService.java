package com.pokewith.auth;

import java.util.Optional;

public interface RedisService {

    Optional<NormalToken> getNormalData(String username);
    void setNormalData(NormalToken normalToken);
    void deleteNormalData(NormalToken normalToken);

    Optional<EmailToken> getEmailData(String id);
    void setEmailData(EmailToken emailToken);
    void deleteEmailData(EmailToken emailToken);
}
