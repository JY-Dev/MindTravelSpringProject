package com.jydev.mindtravel.domain.mind.share.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostCommentRequest {
    private Long memberId;
    private Long postId;
    private String content;
}
