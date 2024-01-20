package com.pokewith.exception;

import com.pokewith.exception.auth.DbErrorException;
import com.pokewith.response.ResponseDto;
import com.pokewith.response.ResponseListDto;
import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.DateTimeException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException : {}", e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    // 기본 유효성검사
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException : {}", e.getMessage());
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        String message = "";
//        for (ConstraintViolation<?> item : violations) {
////            message = ((PathImpl) item.getPropertyPath()).getLeafNode().getName() + item.getMessage();
//            message = item.getMessage();
//        }
        return new ResponseEntity<>("유효하지 않은 입력값 입니다.", HttpStatus.BAD_REQUEST);
    }

    // 커스텀 유효성검사
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseListDto> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ResponseDto> list = fieldErrors.stream()
                .map(o -> ResponseDto.builder()
                    .field(o.getField())
                    .objectName(o.getObjectName())
//                    .message(o.getDefaultMessage())
                    .build())
                .collect(Collectors.toList());


        return new ResponseEntity<>(ResponseListDto.builder()
                            .responseDtoList(list)
                            .build(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e){
//        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
//    }

    // 없는 키로 외래키 잡으려고 할때
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        log.error("SQLIntegrityConstraintViolationException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RedisException.class)
    public ResponseEntity<String> handleRedisException(RedisException e) {
        log.error("RedisException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        log.error("BadRequestException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<String> handleDateTimeException(DateTimeException e) {
        log.error("DateTimeException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DbErrorException.class)
    public ResponseEntity<String> handleDbErrorException(DbErrorException e) {
        log.error("DbErrorException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException e) {
        log.error("ConflictException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<String> handleSizeLimitExceededException(SizeLimitExceededException e) {
        log.error("SizeLimitExceededException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error("NotFoundException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException : {}", e.getMessage());
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

}
