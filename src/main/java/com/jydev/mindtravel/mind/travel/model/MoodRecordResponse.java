package com.jydev.mindtravel.mind.travel.model;

import com.jydev.mindtravel.mind.travel.domain.MoodRecord;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MoodRecordResponse {
    private String content;
    private Mood mood;
    private LocalDateTime createdDate;

    public MoodRecordResponse(MoodRecord moodRecord) {
        this.content = moodRecord.getContent();
        this.mood = moodRecord.getMood();
        this.createdDate = moodRecord.getCreatedDate();
    }
}
