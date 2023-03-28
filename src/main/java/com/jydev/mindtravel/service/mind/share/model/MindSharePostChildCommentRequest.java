package com.jydev.mindtravel.service.mind.share.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostChildCommentRequest {
    private String content;
    private Long parentCommentId;
}
