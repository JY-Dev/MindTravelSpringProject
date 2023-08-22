package com.jydev.mindtravel.web.security.oauth.resolver;

import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverOauthTokenResolver implements OauthTokenResolver {
    private final RestTemplate restTemplate;

    @Override
    public OauthInfo resolve(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<Map> response = restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, request, Map.class);
        Map<String, Object> post = response.getBody();
        Map<String, Object> result = (Map<String, Object>) post.get("response");
        String email = (String) result.get("email");
        String id = (String) result.get("id");
        return new OauthInfo(id, email);
    }

    @Override
    public boolean supports(OauthServerType type) {
        return type.equals(OauthServerType.NAVER);
    }
}
