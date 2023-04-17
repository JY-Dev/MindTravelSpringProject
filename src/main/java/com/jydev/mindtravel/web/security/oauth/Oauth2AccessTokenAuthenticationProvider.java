package com.jydev.mindtravel.web.security.oauth;

import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.member.model.MemberRole;
import com.jydev.mindtravel.service.member.service.MemberService;
import com.jydev.mindtravel.web.security.oauth.model.Oauth2AuthenticationToken;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.resolver.OauthTokenResolver;
import com.jydev.mindtravel.web.security.oauth.resolver.OauthTokenResolverFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Oauth2AccessTokenAuthenticationProvider implements AuthenticationProvider {
    private final OauthTokenResolverFactory factory;
    private final MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Oauth2AuthenticationToken token = (Oauth2AuthenticationToken) authentication;
        OauthTokenResolver resolver = factory.getResolver(token.getType());
        OauthInfo oauthInfo = resolver.resolve(token.getAccessToken());
        MemberDto memberDto = memberService.socialLogin(token.getType(), oauthInfo,token.getFcmToken());
        List<GrantedAuthority> grantedAuthorityList = getAuthorities(memberDto);
        return new UsernamePasswordAuthenticationToken(memberDto.getEmail(),null,grantedAuthorityList);
    }

    private List<GrantedAuthority> getAuthorities(MemberDto memberDto){
        List<GrantedAuthority> result = new ArrayList<>();
        MemberRole role = memberDto.getRole();
        result.add(new SimpleGrantedAuthority(role.name()));
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Oauth2AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
