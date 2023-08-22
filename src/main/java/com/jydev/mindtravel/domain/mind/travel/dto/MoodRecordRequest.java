package com.jydev.mindtravel.domain.mind.travel.dto;

import com.jydev.mindtravel.domain.mind.travel.domain.Mood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MoodRecordRequest {
    private String content;
    private Mood mood;
}
