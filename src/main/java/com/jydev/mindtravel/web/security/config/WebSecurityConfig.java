package com.jydev.mindtravel.web.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.jwt.JwtProvider;
import com.jydev.mindtravel.member.service.MemberService;
import com.jydev.mindtravel.web.http.HttpUtils;
import com.jydev.mindtravel.web.security.AuthenticationEntryPoint;
import com.jydev.mindtravel.web.security.jwt.AuthenticationJwtReturnHandler;
import com.jydev.mindtravel.web.security.jwt.JwtAuthenticationFilter;
import com.jydev.mindtravel.web.security.oauth.Oauth2AccessTokenAuthenticationProvider;
import com.jydev.mindtravel.web.security.oauth.Oauth2AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final AuthenticationEntryPoint entryPoint;
    private final Oauth2AccessTokenAuthenticationProvider provider;
    private final JwtProvider jwtProvider;
    private final ObjectMapper mapper;
    private final HttpUtils httpUtils;
    private final AuthenticationJwtReturnHandler jwtReturnHandler;

    private final MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .securityMatcher(AntPathRequestMatcher.antMatcher("/v1/**"))
                .authorizeHttpRequests()
                .anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(entryPoint);
        http.addFilterBefore(new Oauth2AuthenticationFilter(jwtProvider,jwtReturnHandler, provider), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new JwtAuthenticationFilter(jwtProvider,httpUtils,memberService,mapper), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
