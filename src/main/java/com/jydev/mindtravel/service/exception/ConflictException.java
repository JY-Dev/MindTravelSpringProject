package com.jydev.mindtravel.service.exception;

import com.jydev.mindtravel.web.http.HttpErrorException;
import jakarta.servlet.http.HttpServletResponse;

public class ConflictException extends HttpErrorException {
    public ConflictException(String message){
        super(HttpServletResponse.SC_CONFLICT,message);
    }
}
