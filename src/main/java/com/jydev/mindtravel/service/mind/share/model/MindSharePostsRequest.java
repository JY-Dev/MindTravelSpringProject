package com.jydev.mindtravel.service.mind.share.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostsRequest {
    private Long pageOffset;
    private Long pageSize;
    private MindSharePostCategory category;
}
