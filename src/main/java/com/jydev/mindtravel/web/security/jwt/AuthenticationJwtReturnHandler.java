package com.jydev.mindtravel.web.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.jwt.Jwt;
import com.jydev.mindtravel.jwt.JwtProvider;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationJwtReturnHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final JwtProvider jwtProvider;
    private final HttpUtils httpUtils;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 성공 : {}", authentication);
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        JwtClaimsInfo jwtClaimsInfo = new JwtClaimsInfo((String) token.getPrincipal(), token.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String test = mapper.writeValueAsString(jwtClaimsInfo);
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtProvider.authKey,test);
        Jwt jwt = jwtProvider.createJwt(claims);
        httpUtils.sendResponse(response,HttpServletResponse.SC_OK,"토큰 발급",jwt);
    }
}
