package com.makersharks.identity.exception;

import org.springframework.stereotype.Component;

public class CustomException extends Exception{
    private String code;
    private String error;

    public CustomException(String code, String error){
        this.code = code;
        this.error = error;
    }

}
