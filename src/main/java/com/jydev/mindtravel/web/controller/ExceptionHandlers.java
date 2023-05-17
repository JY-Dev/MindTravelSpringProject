package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpErrorException;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlers {
    private final HttpUtils httpUtils;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse<EmptyResponse>> handle() {
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"허용 되지 않는 메소드 입니다.",null);
    }

    @ExceptionHandler(HttpErrorException.class)
    public ResponseEntity<HttpResponse<EmptyResponse>> handle(HttpErrorException exception) {
        log.error("HttpErrorException : ",exception);
        return httpUtils.makeHttpResponse(exception.getHttpStatusCode(),exception.getMessage(),null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse<EmptyResponse>> handle(Exception e){
        log.error("Exception : ",e);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"서버 오류 입니다.",null);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<HttpResponse<EmptyResponse>> handle(OptimisticLockingFailureException exception){
        log.error("OptimisticLockingFailureException : ", exception);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_BAD_REQUEST,"처리 중인 작업이 있습니다.",null);
    }
}
