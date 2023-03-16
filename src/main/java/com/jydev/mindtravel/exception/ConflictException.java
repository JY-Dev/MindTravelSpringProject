package com.jydev.mindtravel.exception;

import com.jydev.mindtravel.web.http.HttpErrorException;
import jakarta.servlet.http.HttpServletResponse;

public class ConflictException extends HttpErrorException {
    public ConflictException(String message){
        super(HttpServletResponse.SC_CONFLICT,message);
    }
}
