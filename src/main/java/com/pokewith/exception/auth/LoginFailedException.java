package com.pokewith.exception.auth;

import com.pokewith.exception.ErrorCode;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED.getMessage());
    }
    public LoginFailedException(Exception ex) {
        super(ex);
    }
}
