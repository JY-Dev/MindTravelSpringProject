package com.jydev.mindtravel.domain.mind.travel.dto;

import com.jydev.mindtravel.domain.mind.travel.domain.Mood;
import com.jydev.mindtravel.domain.mind.travel.domain.MoodRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MoodRecordResponse {
    private Long moodRecordId;
    private String content;
    private Mood mood;
    private LocalDateTime createdDate;

    public MoodRecordResponse(MoodRecord moodRecord) {
        this.moodRecordId = moodRecord.getId();
        this.content = moodRecord.getContent();
        this.mood = moodRecord.getMood();
        this.createdDate = moodRecord.getCreatedDate();
    }
}
