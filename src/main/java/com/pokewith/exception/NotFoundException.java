package com.pokewith.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND.getMessage());
    }
    public NotFoundException(Exception ex) {
        super(ex);
    }
}
