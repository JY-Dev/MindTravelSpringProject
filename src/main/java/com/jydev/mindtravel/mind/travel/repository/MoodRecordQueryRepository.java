package com.jydev.mindtravel.mind.travel.repository;

import com.jydev.mindtravel.mind.travel.domain.MoodRecord;

import java.util.List;

public interface MoodRecordQueryRepository {
    List<MoodRecord> searchMoodRecord(String email, String date);
}
