package com.makersharks.identity.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
@Builder
public class CustomException extends Exception{
    private String code;
    private String message;

    public static CustomException with(ExceptionType exceptionType, String message){
        return CustomException.builder()
                .code(exceptionType.getCode())
                .message(exceptionType.getMessage())
                .build();
    }

}
