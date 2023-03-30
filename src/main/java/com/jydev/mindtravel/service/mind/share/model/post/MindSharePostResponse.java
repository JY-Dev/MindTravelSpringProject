package com.jydev.mindtravel.service.mind.share.model.post;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class MindSharePostResponse {
    private Long id;
    private String nickname;
    private String title;
    private Long viewCount;
    private Long commentCount;
    private LocalDateTime createdDate;
}
