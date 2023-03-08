package com.jydev.mindtravel.web.security.oauth.resolver;

import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOauthTokenResolver implements OauthTokenResolver{
    private final RestTemplate restTemplate;

    @Override
    public OauthInfo resolve(String token) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://oauth2.googleapis.com/tokeninfo")
                .queryParam("id_token", token)
                .encode()
                .build()
                .toUri();
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        Map<String, Object> post = response.getBody();
        String email = (String) post.get("email");
        String sub = (String) post.get("sub");
        return new OauthInfo(sub,email);
    }

    @Override
    public boolean supports(OauthServerType type) {
        return type.equals(OauthServerType.GOOGLE);
    }
}
