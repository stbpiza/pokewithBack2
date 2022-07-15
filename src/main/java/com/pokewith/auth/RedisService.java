package com.pokewith.auth;

import java.util.Optional;

public interface RedisService {

    // RedisUtil 에서 구현

    Optional<NormalToken> getNormalData(String username);
    void setNormalData(NormalToken normalToken);
    void deleteNormalData(NormalToken normalToken);

    Optional<GitToken> getGitData(String id);
    void setGitData(GitToken gitToken);
    void deleteGitData(GitToken gitToken);

    Optional<EmailToken> getEmailData(String id);
    void setEmailData(EmailToken emailToken);
    void deleteEmailData(EmailToken emailToken);
}
