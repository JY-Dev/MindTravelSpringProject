package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/v1/mind/share")
@Controller
public class MindShareController {
    private final HttpUtils httpUtils;
    @PostMapping("/post")
    public ResponseEntity<HttpResponse<EmptyResponse>> writeMindSharePost(){

        return httpUtils.makeEmptyResponse();
    }
}
