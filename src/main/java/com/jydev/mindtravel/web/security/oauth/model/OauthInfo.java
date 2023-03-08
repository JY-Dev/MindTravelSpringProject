package com.jydev.mindtravel.web.security.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthInfo {
    private String oauthId;
    private String email;
}
