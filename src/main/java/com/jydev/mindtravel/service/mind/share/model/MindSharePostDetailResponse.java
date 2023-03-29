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
    private String content;
    private Long likeCount;
    private Long viewCount;
    private Long commentCount;
    private LocalDateTime createdDate;
    private List<MindSharePostCommentResponse> comments;

    public MindSharePostDetailResponse(MindSharePost mindSharePost) {
        this.postId = mindSharePost.getId();
        this.nickname = mindSharePost.getMember().getNickname();
        this.title = mindSharePost.getTitle();
        this.content = mindSharePost.getContent();
        this.likeCount = mindSharePost.getLikeCount();
        this.viewCount = mindSharePost.getViewCount();
        this.commentCount = mindSharePost.getComments().stream().map(c -> c.getChildComments().size() + 1L).reduce(Long::sum).orElse(0L);
        this.createdDate = mindSharePost.getCreatedDate();
        this.comments = mindSharePost.getComments().stream().map(MindSharePostCommentResponse::new).toList();
    }
}
