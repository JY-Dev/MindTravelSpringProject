package com.jydev.mindtravel.domain.mind.travel.repository;

import com.jydev.mindtravel.domain.mind.travel.domain.MoodRecord;

import java.util.List;
import java.util.Optional;

public interface MoodRecordQueryRepository {
    List<MoodRecord> searchMoodRecords(String email, String date);

    Optional<MoodRecord> findMoodRecord(String email, Long id);
}
