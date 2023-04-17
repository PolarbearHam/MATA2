package com.ssafy.mata.exception;


import com.ssafy.mata.dto.ErrorResponse;
import com.ssafy.mata.util.RedisKeyExecption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler({NoSuchMemberException.class,
            NoSuchProjectException.class,
            RedisKeyExecption.class})
    public ResponseEntity<ErrorResponse> handleNoSuchException(final  RuntimeException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.warn("NoSuchExecption - ", e.getClass()," : ", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(errorResponse);
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(final  RuntimeException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }



}
