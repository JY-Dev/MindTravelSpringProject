package com.jydev.mindtravel.service.mind.share.model.like;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePostLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MindSharePostLikeResponse {
    private Long postId;
    private String nickname;

    public MindSharePostLikeResponse(MindSharePostLike postLike) {
        postId = postLike.getPostId();
        nickname = postLike.getMember().getNickname();
    }
}
