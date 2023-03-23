package com.jydev.mindtravel.service.mind.share.model;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MindSharePostResponse {
    private Long postId;
    private String nickname;
    private String title;
    private Long likeCount;
    private Long viewCount;
    private LocalDateTime createdDate;


    public MindSharePostResponse(MindSharePost mindSharePost) {
        this.nickname = mindSharePost.getMember().getNickname();
        this.title = mindSharePost.getTitle();
        this.likeCount = mindSharePost.getLikeCount();
        this.viewCount = mindSharePost.getViewCount();
        this.createdDate = mindSharePost.getCreatedDate();
        this.postId = mindSharePost.getId();
    }
}
