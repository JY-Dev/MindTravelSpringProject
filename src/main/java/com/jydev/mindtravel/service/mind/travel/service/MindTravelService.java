package com.jydev.mindtravel.service.mind.travel.service;

import com.jydev.mindtravel.service.exception.ClientException;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.service.mind.travel.domain.MoodRecord;
import com.jydev.mindtravel.service.mind.travel.model.MoodRecordRequest;
import com.jydev.mindtravel.service.mind.travel.model.MoodRecordResponse;
import com.jydev.mindtravel.service.mind.travel.repository.MoodRecordCommandRepository;
import com.jydev.mindtravel.service.mind.travel.repository.MoodRecordQueryRepository;
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
