package com.jydev.mindtravel.web.http;

import lombok.Getter;

@Getter
public class HttpErrorException extends RuntimeException{
    private final int httpStatusCode;
    private final String message;
    public HttpErrorException(int httpStatusCode, String message){
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
