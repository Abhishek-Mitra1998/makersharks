package com.makersharks.identity.config;

import com.makersharks.identity.constants.ApiResponse;
import com.makersharks.identity.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Component
@Slf4j
public class RestControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(final Exception ex){
        log.info("Exception Encountered ==>{}",ex.getMessage());
        return ResponseEntity.ok( ApiResponse.<String>builder().success(false).error(ex.getMessage()).build()
        );
    }

}
