package com.jydev.mindtravel.domain.mind.share.dto.post;

import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostCategory;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostsRequest {
    private Long pageOffset;
    private Integer pageSize;
    private MindSharePostCategory category;
}
