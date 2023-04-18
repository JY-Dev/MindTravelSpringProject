package com.jydev.mindtravel.web.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.auth.repository.RefreshTokenRepository;
import com.jydev.mindtravel.auth.jwt.Jwt;
import com.jydev.mindtravel.auth.jwt.JwtProvider;
import com.jydev.mindtravel.service.member.service.MemberService;
import com.jydev.mindtravel.web.http.HttpUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtRefreshFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final HttpUtils httpUtils;
    private final ObjectMapper mapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RequestMatcher matcher = new AntPathRequestMatcher("/v1/reissue/token");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!matcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = jwtProvider.extractToken(request);
            Claims claims = jwtProvider.getClaims(token);
            JwtClaimsInfo claimsInfo = getJwtClaimsInfo(claims);
            String email = claimsInfo.getEmail();
            String findToken = refreshTokenRepository.findRefreshToken(email);
            if (token.equals(findToken)) {
                String accessToken = jwtProvider.createToken(claims, jwtProvider.getExpireDateAccessToken());
                Jwt jwt = new Jwt(accessToken, token);
                refreshTokenRepository.saveRefreshToken(email,token);
                httpUtils.sendResponse(response, HttpServletResponse.SC_OK, "", jwt);
            } else
                throw new IllegalArgumentException();
        } catch (ExpiredJwtException e) {
            JwtClaimsInfo claimsInfo = getJwtClaimsInfo(e.getClaims());
            memberService.logout(claimsInfo.getEmail());
            httpUtils.sendResponse(response, HttpServletResponse.SC_FORBIDDEN, "", null);
        } catch (Exception e) {
            log.error("JwtRefresh Exception :", e);
            httpUtils.sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "", null);
        }
    }

    private JwtClaimsInfo getJwtClaimsInfo(Claims claims) throws JsonProcessingException {
        String json = claims.get(JwtProvider.authKey, String.class);
        return mapper.readValue(json, JwtClaimsInfo.class);
    }
}
