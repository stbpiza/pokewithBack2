package com.pokewith.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN.getMessage());
    }
    public ForbiddenException(Exception ex) {
        super(ex);
    }
}
