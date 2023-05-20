package com.jydev.mindtravel.service.exception;

import com.jydev.mindtravel.web.http.HttpErrorException;
import org.apache.http.HttpStatus;

public class PaymentFailException extends HttpErrorException {
    public PaymentFailException(String message) {
        super(HttpStatus.SC_BAD_REQUEST, message);
    }
}
