package com.jydev.mindtravel.service.mind.share.model.comment;

import com.jydev.mindtravel.service.member.model.MemberResponse;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostChildComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MindSharePostChildCommentResponse {
    private Long commentId;
    private String content;
    private MemberResponse member;
    private String tagNickname;
    private LocalDateTime createdDate;

    public MindSharePostChildCommentResponse(MindSharePostChildComment comment){
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.member = new MemberResponse(comment.getMember());
        if(comment.getTagMember() == null)
            this.tagNickname = "";
        else
            this.tagNickname = comment.getTagMember().getNickname();
        this.createdDate = comment.getCreatedDate();
    }
}
