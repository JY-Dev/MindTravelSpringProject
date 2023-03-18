package com.jydev.mindtravel.service.mind.travel.repository;

import com.jydev.mindtravel.service.mind.travel.domain.MoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRecordCommandRepository extends JpaRepository<MoodRecord,Long> {
}
