package com.pokewith.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TIME_OUT_TOKEN(HttpStatus.UNAUTHORIZED, "기간이 만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "로그인 실패"),
    ETC_TOKEN(HttpStatus.UNAUTHORIZED, "기타 토큰 에러"),
    DB_SAME_ERROR(HttpStatus.CONFLICT, "db 중복 에러"),
    EMAIL_TIME_OUT(HttpStatus.BAD_REQUEST, "email 인증 유효시간 만료"),
    DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알수없는 db 에러"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 값 요청"),
    CONFLICT(HttpStatus.CONFLICT, "409"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한없음"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401");


    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message){
        this.status = status;
        this.message = message;
    }
}
