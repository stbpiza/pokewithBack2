package com.pokewith.exception.auth;

import com.pokewith.exception.ErrorCode;

public class DbErrorException extends RuntimeException {
    public DbErrorException() {
        super(ErrorCode.DB_ERROR.getMessage());
    }
    public DbErrorException(Exception ex) {
        super(ex);
    }
}
