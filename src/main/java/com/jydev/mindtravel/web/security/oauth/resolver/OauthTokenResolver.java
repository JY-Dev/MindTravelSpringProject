package com.jydev.mindtravel.web.security.oauth.resolver;

import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;

public abstract class OauthTokenResolver {
    abstract public OauthInfo resolve(String token);
    abstract public boolean supports(OauthServerType type);
}
