package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.jwt.Jwt;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("v1")
@RestController
public class AuthController {

    private final HttpUtils httpUtils;
    @PostMapping("/login/oauth2/{oauthServerType}")
    public ResponseEntity<HttpResponse<Jwt>> socialLogin(@PathVariable String oauthServerType){
        return httpUtils.makeHttpResponse(HttpStatus.OK.value(),"",new Jwt("accessToken","refreshToken"));
    }
}
