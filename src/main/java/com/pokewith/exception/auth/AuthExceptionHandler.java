package com.pokewith.exception.auth;

import com.pokewith.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class AuthExceptionHandler {

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<String> handleTokenInvalidException(TokenInvalidException e) {
        return new ResponseEntity<>("", ErrorCode.INVALID_TOKEN.getStatus());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException e) {
        return new ResponseEntity<>("", ErrorCode.INVALID_TOKEN.getStatus());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        return new ResponseEntity<>("", ErrorCode.TIME_OUT_TOKEN.getStatus());
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<String> handleLoginFailedException(LoginFailedException e) {
        return new ResponseEntity<>("", ErrorCode.LOGIN_FAILED.getStatus());
    }

    @ExceptionHandler(EmailTimeOutException.class)
    public ResponseEntity<String> handleEmailTimeOutException(EmailTimeOutException e) {
        return new ResponseEntity<>("", ErrorCode.EMAIL_TIME_OUT.getStatus());
    }

    // DB에 Unique 걸린 항목 중복 입력시 발생하는 예외처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>("", ErrorCode.DB_SAME_ERROR.getStatus());
    }
}
