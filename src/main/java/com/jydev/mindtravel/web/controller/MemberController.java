package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.member.model.MemberDto;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final HttpUtils httpUtils;

    @GetMapping
    public ResponseEntity<HttpResponse<MemberDto>> getMember(@RequestAttribute("member") MemberDto member) {
        return httpUtils.makeHttpResponse(HttpStatus.OK.value(), "응답 완료",member);
    }
}
