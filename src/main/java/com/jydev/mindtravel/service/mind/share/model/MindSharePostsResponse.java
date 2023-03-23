package com.jydev.mindtravel.service.mind.share.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MindSharePostsResponse {
    private Long totalPostSize;
    private List<MindSharePostResponse> posts;
}
