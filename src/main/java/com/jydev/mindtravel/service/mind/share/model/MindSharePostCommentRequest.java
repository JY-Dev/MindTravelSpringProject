package com.jydev.mindtravel.service.mind.share.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostCommentRequest {
    private String content;
    private Long postId;
}
