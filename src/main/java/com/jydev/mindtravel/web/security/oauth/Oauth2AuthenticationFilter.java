package com.jydev.mindtravel.web.security.oauth;

import com.jydev.mindtravel.web.security.oauth.model.Oauth2AuthenticationToken;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class Oauth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String MATCH_URL_PREFIX = "/login/oauth2/";
    private static final String ACCESS_TOKEN_HEADER = "Authorization";
    private static final String tokenType = "Bearer ";

    public Oauth2AuthenticationFilter(AuthenticationSuccessHandler authenticationSuccessHandler,
                                      AuthenticationProvider... authenticationProvider) {
        super(new AntPathRequestMatcher(MATCH_URL_PREFIX + "*"));
        this.setAuthenticationManager(new ProviderManager(authenticationProvider));
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        OauthServerType serverType = extractOauthServerType(request);
        String accessToken = extractToken(request);
        return getAuthenticationManager().authenticate(new Oauth2AuthenticationToken(accessToken, serverType));
    }

    public String extractToken(HttpServletRequest request){
        String header = request.getHeader(ACCESS_TOKEN_HEADER);
        if(!header.startsWith(tokenType))
            throw new AuthenticationServiceException("토큰이 존재하지 않습니다.");
        return header.substring(tokenType.length());
    }

    public OauthServerType extractOauthServerType(HttpServletRequest request) {
        String extractType = request.getRequestURI().substring(MATCH_URL_PREFIX.length());
        return Arrays.stream(OauthServerType.values())
                .filter(type -> type.name().toLowerCase().equals(extractType))
                .findFirst()
                .orElseThrow(() -> new AuthenticationServiceException("잘못된 요청 입니다."));
    }
}
