package com.jydev.mindtravel.service.mind.share.model;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MindSharePostResponse {
    private Long postId;
    private String nickname;
    private String title;
    private Long likeCount;
    private Long viewCount;
    private Long commentCount;
    private LocalDateTime createdDate;
}
