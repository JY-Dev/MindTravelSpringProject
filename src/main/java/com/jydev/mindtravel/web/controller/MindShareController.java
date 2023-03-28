package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostsRequest;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostsResponse;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostRequest;
import com.jydev.mindtravel.service.mind.share.service.MindShareService;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/mind/share")
@Controller
public class MindShareController {
    private final HttpUtils httpUtils;
    private final MindShareService mindShareService;

    @PostMapping("/post")
    public ResponseEntity<HttpResponse<EmptyResponse>> saveMindSharePost(@RequestAttribute("member") MemberDto member,
                                                                         @RequestBody MindSharePostRequest request) {
        mindShareService.saveMindSharePost(member.getEmail(), request);
        return httpUtils.makeEmptyResponse();
    }

    @GetMapping("/post")
    public ResponseEntity<HttpResponse<MindSharePostsResponse>> searchMindSharePosts(Long pageOffset, Integer pageSize, MindSharePostCategory category) {
        MindSharePostsRequest request = MindSharePostsRequest.builder()
                .pageOffset(pageOffset)
                .pageSize(pageSize)
                .category(category)
                .build();
        MindSharePostsResponse data = mindShareService.searchMindSharePosts(request);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK,"",data);
    }
}
