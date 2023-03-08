package com.jydev.mindtravel.web.security.oauth.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@ToString
public class Oauth2AuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private String accessToken;
    private OauthServerType type;

    public Oauth2AuthenticationToken(String accessToken, OauthServerType type){
        super(null);
        this.accessToken = accessToken;
        this.type = type;
        setAuthenticated(false);
    }

    public Oauth2AuthenticationToken(Object principal,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
