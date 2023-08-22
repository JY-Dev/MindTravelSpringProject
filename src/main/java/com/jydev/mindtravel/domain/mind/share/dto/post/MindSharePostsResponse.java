package com.jydev.mindtravel.domain.mind.share.dto.post;

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
