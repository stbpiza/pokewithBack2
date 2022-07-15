package com.pokewith.exception.auth;

import com.pokewith.exception.ErrorCode;

public class EmailTimeOutException extends RuntimeException {
    public EmailTimeOutException() {
        super(ErrorCode.EMAIL_TIME_OUT.getMessage());
    }
    public EmailTimeOutException(Exception ex) {
        super(ex);
    }
}
