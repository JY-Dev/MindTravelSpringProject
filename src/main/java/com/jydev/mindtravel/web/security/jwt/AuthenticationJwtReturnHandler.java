package com.jydev.mindtravel.web.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.jwt.Jwt;
import com.jydev.mindtravel.jwt.JwtProvider;
import com.jydev.mindtravel.web.http.HttpResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationJwtReturnHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final JwtProvider jwtProvider;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtProvider.authKey,mapper.writeValueAsString(token));
        Jwt jwt = jwtProvider.createJwt(claims);
        HttpResponse<Jwt> httpResponse = new HttpResponse<>(HttpServletResponse.SC_OK,"토큰 발급",jwt);
        String json = mapper.writeValueAsString(httpResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}
