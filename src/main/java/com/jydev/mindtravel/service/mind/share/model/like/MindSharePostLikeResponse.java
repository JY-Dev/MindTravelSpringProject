package com.jydev.mindtravel.service.mind.share.model.like;

import com.jydev.mindtravel.service.member.model.MemberResponse;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MindSharePostLikeResponse {
    private Long postId;
    private MemberResponse member;
    private LocalDateTime createdDate;

    public MindSharePostLikeResponse(MindSharePostLike postLike) {
        this.postId = postLike.getPostId();
        this.member = new MemberResponse(postLike.getMember());
        this.createdDate = postLike.getCreatedDate();
    }
}
