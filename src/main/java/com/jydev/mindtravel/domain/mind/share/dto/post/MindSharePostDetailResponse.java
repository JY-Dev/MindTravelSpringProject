package com.jydev.mindtravel.domain.mind.share.dto.post;

import com.jydev.mindtravel.domain.member.dto.MemberResponse;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostCategory;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentResponse;
import com.jydev.mindtravel.domain.mind.share.dto.like.MindSharePostLikeResponse;
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
    private MindSharePostCategory category;
    private Long viewCount;
    private Long commentCount;
    private LocalDateTime createdDate;
    private MemberResponse member;
    private List<MindSharePostCommentResponse> comments;
    private List<MindSharePostLikeResponse> likes;

    public MindSharePostDetailResponse(Long viewCount, MindSharePost mindSharePost) {
        this.postId = mindSharePost.getId();
        this.member = new MemberResponse(mindSharePost.getMember());
        this.category = mindSharePost.getCategory();
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
