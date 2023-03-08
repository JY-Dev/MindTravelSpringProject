package com.jydev.mindtravel.web.security.oauth.resolver;

import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOauthTokenResolver {
    private final RestTemplate restTemplate;
    public OauthInfo getGoogleOauthInfo(String token) {
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




    public void setHeaders(String accessToken, HttpHeaders headers) {
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }
}
