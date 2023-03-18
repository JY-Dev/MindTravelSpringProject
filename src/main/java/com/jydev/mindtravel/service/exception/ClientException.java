package com.jydev.mindtravel.service.exception;

import com.jydev.mindtravel.web.http.HttpErrorException;
import jakarta.servlet.http.HttpServletResponse;

public class ClientException extends HttpErrorException {
    public ClientException(String message){
        super(HttpServletResponse.SC_BAD_REQUEST,message);
    }
}
