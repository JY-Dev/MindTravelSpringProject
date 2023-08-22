package com.jydev.mindtravel.domain.mind.share.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MindSharePostChildCommentRequest {
    private String content;
    private Long postId;
    private Long tagMemberId;
    private Long parentCommentId;

    public boolean hasTagMember() {
        return !tagMemberId.equals(-1L);
    }
}
