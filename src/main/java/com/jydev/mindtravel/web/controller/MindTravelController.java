package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.member.model.MemberDto;
import com.jydev.mindtravel.mind.travel.model.MoodRecordRequest;
import com.jydev.mindtravel.mind.travel.service.MindTravelService;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/mind/travel")
public class MindTravelController {
    private final HttpUtils httpUtils;
    private final MindTravelService mindTravelService;

    @PostMapping("/record-mood")
    public ResponseEntity<HttpResponse<EmptyResponse>> recordMood(@RequestAttribute("member") MemberDto member,
                                                                     @RequestBody MoodRecordRequest request) {
        mindTravelService.recordMood(member.getEmail(),request);
        return httpUtils.makeEmptyResponse();
    }
}
