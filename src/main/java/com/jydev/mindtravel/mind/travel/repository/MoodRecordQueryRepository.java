package com.jydev.mindtravel.mind.travel.repository;

import com.jydev.mindtravel.mind.travel.domain.MoodRecord;

import java.util.List;

public interface MoodRecordQueryRepository {
    List<MoodRecord> searchMoodRecords(String email, String date);
}
