package com.jydev.mindtravel.domain.auth.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Jwt {
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public Jwt(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}