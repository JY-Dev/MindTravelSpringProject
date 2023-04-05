package com.jydev.mindtravel.service.mind.share.model.post;

import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
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
