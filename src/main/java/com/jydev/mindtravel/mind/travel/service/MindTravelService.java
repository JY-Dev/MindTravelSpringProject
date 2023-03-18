package com.jydev.mindtravel.mind.travel.service;

import com.jydev.mindtravel.exception.ClientException;
import com.jydev.mindtravel.member.domain.Member;
import com.jydev.mindtravel.member.repository.member.MemberQueryRepository;
import com.jydev.mindtravel.mind.travel.domain.MoodRecord;
import com.jydev.mindtravel.mind.travel.model.MoodRecordRequest;
import com.jydev.mindtravel.mind.travel.model.MoodRecordResponse;
import com.jydev.mindtravel.mind.travel.repository.MoodRecordCommandRepository;
import com.jydev.mindtravel.mind.travel.repository.MoodRecordQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MindTravelService {
    private final MemberQueryRepository memberQueryRepository;
    private final MoodRecordCommandRepository moodRecordCommandRepository;
    private final MoodRecordQueryRepository moodRecordQueryRepository;

    public void recordMood(String email, MoodRecordRequest request) {
        Member member = memberQueryRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException("멤버 검색 실패"));
        moodRecordCommandRepository.save(new MoodRecord(request, member));
    }

    public List<MoodRecordResponse> fetchRecordMoods(String email, String date) {
        return moodRecordQueryRepository.searchMoodRecords(email, date)
                .stream().map(MoodRecordResponse::new)
                .toList();
    }

}
