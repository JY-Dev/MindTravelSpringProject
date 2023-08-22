package com.jydev.mindtravel.common.exception;

import org.apache.http.HttpStatus;

public class NotFoundException extends BusinessException {
    public static final String DEFAULT = "결과가 없습니다.";
    public static final String MEMBER = "검색된 유저가 없습니다.";
    public static final String POST = "글이 존재하지 않습니다.";
    public static final String COMMENT = "댓글 정보가 없습니다.";

    public NotFoundException(String message) {
        super(HttpStatus.SC_NOT_FOUND, message);
    }
}
