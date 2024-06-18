package com.makersharks.identity.exception;

import lombok.Getter;

public enum ExceptionType {
    USER_NOT_FOUND("E-OO1","Password does not match");

            @Getter
            String code;
            @Getter
            String message;

    ExceptionType(String code, String message){
        this.code = code;
        this.message = message;
    }
}

