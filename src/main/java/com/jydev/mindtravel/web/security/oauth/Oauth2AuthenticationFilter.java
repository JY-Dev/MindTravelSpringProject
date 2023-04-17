package com.jydev.mindtravel.web.security.oauth;

import com.jydev.mindtravel.auth.jwt.JwtProvider;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class Oauth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String MATCH_URL_PREFIX = "/v1/login/oauth2/";
    private final JwtProvider jwtProvider;

    public Oauth2AuthenticationFilter(JwtProvider jwtProvider, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler,
                                      AuthenticationProvider... authenticationProvider) {
        super(new AntPathRequestMatcher(MATCH_URL_PREFIX + "*"));
        this.jwtProvider = jwtProvider;
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
        this.setAuthenticationManager(new ProviderManager(authenticationProvider));
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        OauthServerType serverType = extractOauthServerType(request);
        String accessToken = jwtProvider.extractToken(request);
        String fcmToken = extractFcmToken(request);
        return getAuthenticationManager().authenticate(new Oauth2AuthenticationToken(accessToken, fcmToken, serverType));
    }

    public String extractFcmToken(HttpServletRequest request){
        try{
            String fcmToken = request.getHeader("FCM");
            if(fcmToken.isEmpty())
                throw new AuthenticationServiceException("");
            return request.getHeader("FCM");
        } catch (Exception e){
            throw new AuthenticationServiceException("FCM 토큰이 없습니다.");
        }

    }

    public OauthServerType extractOauthServerType(HttpServletRequest request) {
        String extractType = request.getRequestURI().substring(MATCH_URL_PREFIX.length());
        return Arrays.stream(OauthServerType.values())
                .filter(type -> type.name().toLowerCase().equals(extractType))
                .findFirst()
                .orElseThrow(() -> new AuthenticationServiceException("잘못된 요청 입니다."));
    }
}
