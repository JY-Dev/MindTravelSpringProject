package com.jydev.mindtravel.common.exception;

import org.apache.http.HttpStatus;

public class InvalidException extends BusinessException {
    public static final String DEFAULT = "올바르지 않은 요청입니다.";
    public static final String MEMBER_NICKNAME = "유효 하지 않은 닉네임 입니다.";
    public static final String DELETE_COMMENT = "댓글을 삭제 할 수 없습니다.";
    public static final String UPDATE_COMMENT = "댓글을 삭제 할 수 없습니다.";

    public InvalidException(String message) {
        super(HttpStatus.SC_BAD_REQUEST, message);
    }
}
