package com.jydev.mindtravel.domain.mind.share.dto.comment;

import com.jydev.mindtravel.domain.member.dto.MemberResponse;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MindSharePostCommentResponse {
    private Long commentId;
    private String content;
    private Boolean isDeleted;
    private MemberResponse member;
    private LocalDateTime createdDate;
    private List<MindSharePostChildCommentResponse> childComments;

    public MindSharePostCommentResponse(MindSharePostComment comment) {
        this.isDeleted = comment.getIsDeleted();
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.member = new MemberResponse(comment.getMember());
        this.createdDate = comment.getCreatedDate();
        this.childComments = comment.getChildComments().stream().map(MindSharePostChildCommentResponse::new).toList();
    }
}
