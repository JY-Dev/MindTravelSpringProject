package com.jydev.mindtravel.mind.travel.repository;

import com.jydev.mindtravel.mind.travel.domain.MoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRecordCommandRepository extends JpaRepository<MoodRecord,Long> {
}
