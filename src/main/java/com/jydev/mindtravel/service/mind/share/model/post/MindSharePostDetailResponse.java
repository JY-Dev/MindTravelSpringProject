package com.jydev.mindtravel.service.mind.share.model.post;

import com.jydev.mindtravel.service.member.model.MemberResponse;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentResponse;
import com.jydev.mindtravel.service.mind.share.model.like.MindSharePostLikeResponse;
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
    private String title;
    private String content;
    private Long viewCount;
    private Long commentCount;
    private LocalDateTime createdDate;
    private MemberResponse member;
    private List<MindSharePostCommentResponse> comments;
    private List<MindSharePostLikeResponse> likes;

    public MindSharePostDetailResponse(Long viewCount,MindSharePost mindSharePost) {
        this.postId = mindSharePost.getId();
        this.member = new MemberResponse(mindSharePost.getMember());
        this.title = mindSharePost.getTitle();
        this.content = mindSharePost.getContent();
        this.likes = mindSharePost.getLikes().stream()
                .map(MindSharePostLikeResponse::new).toList();
        this.viewCount = viewCount;
        this.commentCount = mindSharePost.getComments().stream().map(c -> c.getChildComments().size() + 1L).reduce(Long::sum).orElse(0L);
        this.createdDate = mindSharePost.getCreatedDate();
        this.comments = mindSharePost.getComments().stream().map(MindSharePostCommentResponse::new).toList();
    }
}
