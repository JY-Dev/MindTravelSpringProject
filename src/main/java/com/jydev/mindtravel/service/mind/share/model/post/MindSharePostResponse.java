package com.jydev.mindtravel.service.mind.share.model.post;

import com.jydev.mindtravel.service.member.model.MemberResponse;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class MindSharePostResponse {
    private Long postId;
    private MemberResponse member;
    private String title;
    private MindSharePostCategory category;
    private Long viewCount;
    private Long commentCount;
    private LocalDateTime createdDate;
}
