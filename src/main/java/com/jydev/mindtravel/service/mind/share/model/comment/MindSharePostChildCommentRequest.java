package com.jydev.mindtravel.service.mind.share.model.comment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostChildCommentRequest {
    private String content;
    private Long tagMemberId;
    private Long parentCommentId;
}
