package com.jydev.mindtravel.common.exception;

import org.apache.http.HttpStatus;

public class PaymentFailException extends BusinessException {
    public PaymentFailException(String message) {
        super(HttpStatus.SC_BAD_REQUEST, message);
    }
}
