package com.jydev.mindtravel.service.mind.share.model.comment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostCommentRequest {
    private String content;
    private Long postId;
}
