package com.jydev.mindtravel.web.exception;

import com.jydev.mindtravel.common.exception.BusinessException;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final HttpUtils httpUtils;

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpResponse<EmptyResponse> handle() {
        return httpUtils.makeHttpResponse("허용 되지 않는 메소드 입니다.", null);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<HttpResponse<EmptyResponse>> handle(BusinessException exception) {
        log.error("HttpErrorException : ", exception);
        return ResponseEntity.status(exception.getHttpStatusCode())
                .body(httpUtils.makeHttpResponse(exception.getMessage(), null));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public HttpResponse<EmptyResponse> handle(Exception e) {
        log.error("Exception : ", e);
        return httpUtils.makeHttpResponse("서버 오류 입니다.", null);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public HttpResponse<EmptyResponse> handle(OptimisticLockingFailureException exception) {
        log.error("OptimisticLockingFailureException : ", exception);
        return httpUtils.makeHttpResponse("처리 중인 작업이 있습니다.", null);
    }
}
