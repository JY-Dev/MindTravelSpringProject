package com.jydev.mindtravel.domain.mind.share.dto.like;

import com.jydev.mindtravel.domain.member.dto.MemberResponse;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostLike;
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
