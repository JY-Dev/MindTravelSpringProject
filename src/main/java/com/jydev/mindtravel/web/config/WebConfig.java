package com.jydev.mindtravel.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.web.resolver.PaymentDtoMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ObjectMapper mapper;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PaymentDtoMethodArgumentResolver(mapper));
    }
}