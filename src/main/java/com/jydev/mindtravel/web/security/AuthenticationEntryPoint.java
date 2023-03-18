package com.jydev.mindtravel.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    private final HttpUtils httpUtils;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        httpUtils.sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "인증 실패", null);
    }
}