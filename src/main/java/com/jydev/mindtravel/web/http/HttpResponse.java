package com.jydev.mindtravel.web.http;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class HttpResponse<T> {
    private String message;
    private T data;
}