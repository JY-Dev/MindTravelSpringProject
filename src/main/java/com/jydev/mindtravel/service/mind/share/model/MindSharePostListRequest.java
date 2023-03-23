package com.jydev.mindtravel.service.mind.share.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostListRequest {
    private Integer pageOffset;
    private Integer pageSize;
    private MindSharePostCategory category;
}
