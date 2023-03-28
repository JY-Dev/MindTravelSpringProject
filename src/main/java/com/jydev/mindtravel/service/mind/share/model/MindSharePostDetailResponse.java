package com.jydev.mindtravel.service.mind.share.model;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MindSharePostDetailResponse {
    private Long postId;
    private String nickname;
    private String title;
    private Long likeCount;
    private Long viewCount;
    private LocalDateTime createdDate;
    private List<MindSharePostCommentResponse> comments;

    public MindSharePostDetailResponse(MindSharePost mindSharePost){
        this.postId = mindSharePost.getId();
        this.nickname = mindSharePost.getMember().getNickname();
        this.title = mindSharePost.getTitle();
        this.likeCount = mindSharePost.getLikeCount();
        this.viewCount = mindSharePost.getViewCount();
        this.createdDate = mindSharePost.getCreatedDate();
        this.comments = mindSharePost.getComments().stream().map(MindSharePostCommentResponse::new).toList();
    }
}
