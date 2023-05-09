package com.jydev.mindtravel.web.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.auth.repository.RefreshTokenRepository;
import com.jydev.mindtravel.auth.jwt.Jwt;
import com.jydev.mindtravel.auth.jwt.JwtProvider;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final HttpUtils httpUtils;

    private final RefreshTokenRepository repository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        JwtClaimsInfo jwtClaimsInfo = new JwtClaimsInfo(token);
        String json = mapper.writeValueAsString(jwtClaimsInfo);
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtProvider.authKey, json);
        Jwt jwt = jwtProvider.createJwt(claims);
        repository.saveRefreshToken(jwtClaimsInfo.getEmail(),jwt.getRefreshToken());
        httpUtils.sendResponse(response, HttpServletResponse.SC_OK, "토큰 발급", jwt);
    }
}
