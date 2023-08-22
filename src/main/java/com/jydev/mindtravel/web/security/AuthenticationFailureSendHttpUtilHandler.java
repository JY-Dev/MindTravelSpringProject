package com.jydev.mindtravel.web.security;

import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthenticationFailureSendHttpUtilHandler implements AuthenticationFailureHandler {
    private final HttpUtils httpUtils;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        httpUtils.sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage(), null);
    }
}
