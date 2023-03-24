package com.jydev.mindtravel.service.mind.share.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MindSharePostsResponse {
    private Long totalPostSize;
    private List<MindSharePostResponse> posts;
}
