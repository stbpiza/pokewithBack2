package com.pokewith.exception.auth;


import com.pokewith.exception.ErrorCode;

public class TokenTimeOutException extends RuntimeException {
    public TokenTimeOutException() {
        super(ErrorCode.TIME_OUT_TOKEN.getMessage());
    }
    public TokenTimeOutException(Exception ex) {
        super(ex);
    }
}
