package com.jydev.mindtravel.domain.mind.share.dto.post;

import com.jydev.mindtravel.domain.member.dto.MemberResponse;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostCategory;
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
