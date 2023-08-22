package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.domain.member.dto.MemberDto;
import com.jydev.mindtravel.domain.member.service.MemberService;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/member")
public class MemberController {
    private final HttpUtils httpUtils;
    private final MemberService memberService;

    @GetMapping
    public HttpResponse<MemberDto> getMember(@RequestAttribute("member") MemberDto member) {
        return httpUtils.makeHttpResponse("", member);
    }

    @PatchMapping("/{nickname}")
    public HttpResponse<MemberDto> editMember(@RequestAttribute("member") MemberDto member,
                                              @PathVariable String nickname) {
        MemberDto editMember = memberService.editNickname(member.getEmail(), nickname);
        return httpUtils.makeHttpResponse("", editMember);
    }
}
