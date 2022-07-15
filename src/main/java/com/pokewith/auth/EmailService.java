package com.pokewith.auth;

public interface EmailService {

    void signUpEmail(String userId, String email);

    void lostPasswordEmail(String userId, String email);
}
