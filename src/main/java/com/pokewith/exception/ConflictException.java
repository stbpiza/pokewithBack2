package com.pokewith.exception;

public class ConflictException extends RuntimeException{
    public ConflictException() {
        super(ErrorCode.BAD_REQUEST.getMessage());
    }
    public ConflictException(Exception ex) {
        super(ex);
    }
}
