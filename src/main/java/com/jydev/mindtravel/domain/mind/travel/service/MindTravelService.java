package com.jydev.mindtravel.domain.mind.travel.service;

import com.jydev.mindtravel.common.exception.NotFoundException;
import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.domain.member.service.MemberUtils;
import com.jydev.mindtravel.domain.mind.travel.domain.MoodRecord;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordRequest;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordResponse;
import com.jydev.mindtravel.domain.mind.travel.repository.MoodRecordCommandRepository;
import com.jydev.mindtravel.domain.mind.travel.repository.MoodRecordQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MindTravelService {
    private final MemberQueryRepository memberQueryRepository;
    private final MoodRecordCommandRepository moodRecordCommandRepository;
    private final MoodRecordQueryRepository moodRecordQueryRepository;

    public void recordMood(String email, MoodRecordRequest request) {
        Member member = MemberUtils.findMemberByEmail(memberQueryRepository, email);
        moodRecordCommandRepository.save(new MoodRecord(request, member));
    }

    public void deleteRecordMood(String email, Long recordMoodId) {
        MoodRecord moodRecord = moodRecordQueryRepository.findMoodRecord(email, recordMoodId)
                .orElseThrow(() -> new NotFoundException(NotFoundException.DEFAULT));
        moodRecordCommandRepository.delete(moodRecord);
    }

    @Transactional(readOnly = true)
    public List<MoodRecordResponse> getRecordMoods(String email, String date) {
        return moodRecordQueryRepository.searchMoodRecords(email, date)
                .stream().map(MoodRecordResponse::new)
                .toList();
    }
}
