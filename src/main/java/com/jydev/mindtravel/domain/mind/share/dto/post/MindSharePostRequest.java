package com.jydev.mindtravel.domain.mind.share.dto.post;

import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MindSharePostRequest {
    private String title;
    private String content;
    private MindSharePostCategory category;
}
