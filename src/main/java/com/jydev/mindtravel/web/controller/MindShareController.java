package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.mind.share.model.WriteMindSharePostRequest;
import com.jydev.mindtravel.service.mind.share.service.MindShareService;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/v1/mind/share")
@Controller
public class MindShareController {
    private final HttpUtils httpUtils;
    private final MindShareService mindShareService;

    @PostMapping("/post")
    public ResponseEntity<HttpResponse<EmptyResponse>> saveMindSharePost(@RequestAttribute("member") MemberDto member,
                                                                          @RequestBody WriteMindSharePostRequest request) {
        mindShareService.saveMindSharePost(member.getEmail(),request);
        return httpUtils.makeEmptyResponse();
    }
}
