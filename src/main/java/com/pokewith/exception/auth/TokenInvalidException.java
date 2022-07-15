package com.pokewith.exception.auth;

import com.pokewith.exception.ErrorCode;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(){
        super(ErrorCode.INVALID_TOKEN.getMessage());
    }
    public TokenInvalidException(Exception ex){ super(ex); }
}
