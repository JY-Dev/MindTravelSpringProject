package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.service.model.MemberDto;
import com.jydev.mindtravel.service.service.MemberService;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/member")
public class MemberController {
    private final HttpUtils httpUtils;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<HttpResponse<MemberDto>> getMember(@RequestAttribute("member") MemberDto member) {
        return httpUtils.makeHttpResponse(HttpStatus.OK.value(), "", member);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<HttpResponse<MemberDto>> editMember(@RequestAttribute("member") MemberDto member,
                                                              String nickname) {
        MemberDto editMember = memberService.editNickname(member.getEmail(), nickname);
        return httpUtils.makeHttpResponse(HttpStatus.OK.value(), "",editMember);
    }
}
