package com.jydev.mindtravel.web.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.auth.jwt.JwtProvider;
import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.member.service.MemberService;
import com.jydev.mindtravel.web.http.HttpUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final HttpUtils httpUtils;
    private final MemberService memberService;
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String accessToken = jwtProvider.extractToken(request);
            Claims claims = jwtProvider.getClaims(accessToken);
            String json = claims.get(JwtProvider.authKey, String.class);
            JwtClaimsInfo claimsInfo = mapper.readValue(json, JwtClaimsInfo.class);
            String email = claimsInfo.getEmail();
            MemberDto member = memberService.findMemberByEmail(email);
            if (member == null)
                throw new AuthenticationServiceException("사용자가 없음");
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(email, null,
                            claimsInfo.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
            request.setAttribute("member",member);
            doFilter(request, response, filterChain);
        } catch (ExpiredJwtException e) {
            httpUtils.sendResponse(response, HttpServletResponse.SC_FORBIDDEN, "토큰 만료", null);
        } catch (Exception e) {
            log.error("Authentication Exception :", e);
            httpUtils.sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "인증 실패", null);
        }
    }
}
