package com.jydev.mindtravel.domain.mind.share.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MindSharePostCommentEditRequest {
    private Long commentId;
    private Long memberId;
    private String content;
}
