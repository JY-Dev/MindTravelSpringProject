package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.domain.member.dto.MemberDto;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordRequest;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordResponse;
import com.jydev.mindtravel.domain.mind.travel.service.MindTravelService;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/mind/travel")
public class MindTravelController {
    private final HttpUtils httpUtils;
    private final MindTravelService mindTravelService;

    @PostMapping("/record-mood")
    public HttpResponse<EmptyResponse> recordMood(@RequestAttribute("member") MemberDto member,
                                                  @RequestBody MoodRecordRequest request) {
        mindTravelService.recordMood(member.getEmail(), request);
        return httpUtils.makeEmptyResponse();
    }

    @DeleteMapping("/record-mood/{moodRecordId}")
    public HttpResponse<EmptyResponse> deleteRecordMood(@RequestAttribute("member") MemberDto member,
                                                        @PathVariable Long moodRecordId) {
        mindTravelService.deleteRecordMood(member.getEmail(), moodRecordId);
        return httpUtils.makeEmptyResponse();
    }

    @GetMapping("/record-moods")
    public HttpResponse<List<MoodRecordResponse>> getRecordMoods(@RequestAttribute("member") MemberDto member, String date) {
        List<MoodRecordResponse> result = mindTravelService.getRecordMoods(member.getEmail(), date);
        return httpUtils.makeHttpResponse("", result);
    }
}
