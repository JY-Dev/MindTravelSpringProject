package com.jydev.mindtravel.service.mind.share.model.post;

import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostsRequest {
    private Long pageOffset;
    private Integer pageSize;
    private MindSharePostCategory category;
}
