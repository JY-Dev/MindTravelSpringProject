package com.jydev.mindtravel.web.security.oauth.resolver;

import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;

public interface OauthTokenResolver {
    OauthInfo resolve(String token);

    boolean supports(OauthServerType type);
}
