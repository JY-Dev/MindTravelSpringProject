package com.jydev.mindtravel.service.mind.travel.repository;

import com.jydev.mindtravel.service.mind.travel.domain.MoodRecord;

import java.util.List;

public interface MoodRecordQueryRepository {
    List<MoodRecord> searchMoodRecords(String email, String date);
    MoodRecord findMoodRecord(String email, Long id);
}
