package com.jydev.mindtravel.common.exception;

import org.apache.http.HttpStatus;

public class ConflictException extends BusinessException {
    public static final String MEMBER = "중복된 닉네임 입니다.";

    public ConflictException(String message) {
        super(HttpStatus.SC_CONFLICT, message);
    }
}
