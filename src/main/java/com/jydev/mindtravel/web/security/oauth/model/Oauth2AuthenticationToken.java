package com.jydev.mindtravel.web.security.oauth.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
@ToString
public class Oauth2AuthenticationToken extends AbstractAuthenticationToken {
    private final String accessToken;
    private final String fcmToken;
    private final OauthServerType type;

    public Oauth2AuthenticationToken(String accessToken, String fcmToken, OauthServerType type) {
        super(null);
        this.fcmToken = fcmToken;
        this.accessToken = accessToken;
        this.type = type;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
