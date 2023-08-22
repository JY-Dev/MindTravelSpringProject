package com.jydev.mindtravel.domain.mind.travel.repository;

import com.jydev.mindtravel.domain.mind.travel.domain.MoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRecordCommandRepository extends JpaRepository<MoodRecord, Long> {
}
