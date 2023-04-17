package com.jydev.mindtravel.web.security.oauth.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@ToString
public class Oauth2AuthenticationToken extends AbstractAuthenticationToken {
    private String accessToken;
    private String fcmToken;
    private OauthServerType type;

    public Oauth2AuthenticationToken(String accessToken, String fcmToken, OauthServerType type){
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
