package com.pokewith.exception;


public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super(ErrorCode.CONFLICT.getMessage());
    }
    public BadRequestException(Exception ex) {
        super(ex);
    }
}
