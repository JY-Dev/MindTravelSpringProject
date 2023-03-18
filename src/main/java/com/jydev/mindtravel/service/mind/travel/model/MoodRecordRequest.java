package com.jydev.mindtravel.service.mind.travel.model;

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
