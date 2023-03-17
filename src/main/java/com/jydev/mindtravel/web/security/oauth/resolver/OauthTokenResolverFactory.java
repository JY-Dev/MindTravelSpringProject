package com.jydev.mindtravel.web.security.oauth.resolver;

import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OauthTokenResolverFactory {
    private final List<OauthTokenResolver> resolvers = new ArrayList<>();

    public OauthTokenResolverFactory(OauthTokenResolver... googleOauthTokenResolver) {
        resolvers.addAll(List.of(googleOauthTokenResolver));
    }

    public OauthTokenResolver getResolver(OauthServerType type) {
        return resolvers.stream()
                .filter(r -> r.supports(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not supports OauthTokenResolver"));
    }
}
