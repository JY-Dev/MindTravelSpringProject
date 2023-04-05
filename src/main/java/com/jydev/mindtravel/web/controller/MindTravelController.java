package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.mind.travel.model.MoodRecordRequest;
import com.jydev.mindtravel.service.mind.travel.model.MoodRecordResponse;
import com.jydev.mindtravel.service.mind.travel.service.MindTravelService;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/mind/travel")
public class MindTravelController {
    private final HttpUtils httpUtils;
    private final MindTravelService mindTravelService;

    @PostMapping("/record-mood")
    public ResponseEntity<HttpResponse<EmptyResponse>> recordMood(@RequestAttribute("member") MemberDto member,
                                                                  @RequestBody MoodRecordRequest request) {
        mindTravelService.recordMood(member.getEmail(), request);
        return httpUtils.makeEmptyResponse();
    }

    @DeleteMapping("/record-mood/{moodRecordId}")
    public ResponseEntity<HttpResponse<EmptyResponse>> deleteRecordMood(@RequestAttribute("member") MemberDto member,
                                                                  @PathVariable Long moodRecordId) {
        mindTravelService.deleteRecordMood(member.getEmail(), moodRecordId);
        return httpUtils.makeEmptyResponse();
    }

    @GetMapping("/record-moods")
    public ResponseEntity<HttpResponse<List<MoodRecordResponse>>> getRecordMoods(@RequestAttribute("member") MemberDto member, String date) {
        List<MoodRecordResponse> result = mindTravelService.getRecordMoods(member.getEmail(), date);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK,"",result);
    }
}
