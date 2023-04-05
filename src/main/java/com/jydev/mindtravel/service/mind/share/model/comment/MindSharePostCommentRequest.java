package com.jydev.mindtravel.service.mind.share.model.comment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostCommentRequest {
    private Long memberId;
    private Long postId;
    private String content;
}
