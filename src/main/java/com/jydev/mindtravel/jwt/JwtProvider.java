package com.jydev.mindtravel.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {
    public final byte[] secretByte;
    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        secretByte = secret.getBytes();
        key = Keys.hmacShaKeyFor(secretByte);
    }

    public static final String authKey = "auth";

    public Jwt createJwt(Map<String, Object> claims) {
        String accessToken = createToken(claims, getExpireDateAccessToken());
        String refreshToken = createToken(claims, getExpireDateRefreshToken());
        return Jwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createToken(Map<String, Object> claims, Date expireDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expireDate)
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Date getExpireDateAccessToken() {
        long expireTimeMils = 1000 * 6;
        return new Date(System.currentTimeMillis() + expireTimeMils);
    }

    public Date getExpireDateRefreshToken() {
        long expireTimeMils = 1000L * 60 * 60 * 24 * 60;
        return new Date(System.currentTimeMillis() + expireTimeMils);
    }

    public String extractToken(HttpServletRequest request) {
        String ACCESS_TOKEN_HEADER = "Authorization";
        String header = request.getHeader(ACCESS_TOKEN_HEADER);
        String tokenType = "Bearer ";
        if (!header.startsWith(tokenType))
            throw new AuthenticationServiceException("토큰이 존재하지 않습니다.");
        return header.substring(tokenType.length());
    }
}