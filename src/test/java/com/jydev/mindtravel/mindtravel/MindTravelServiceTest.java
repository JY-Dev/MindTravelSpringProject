package com.jydev.mindtravel.mindtravel;

import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.mind.travel.domain.MoodRecord;
import com.jydev.mindtravel.domain.mind.travel.domain.Mood;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordRequest;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordResponse;
import com.jydev.mindtravel.domain.mind.travel.repository.MoodRecordQueryRepository;
import com.jydev.mindtravel.domain.mind.travel.service.MindTravelService;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MindTravelServiceTest {
    @Mock
    private MoodRecordQueryRepository moodRecordQueryRepository;
    @InjectMocks
    private MindTravelService mindTravelService;


    @Test
    public void fetchRecordMoodsTest(){
        String email = "test@naver.com";
        String id = "id";
        OauthInfo oauthInfo = new OauthInfo(id, email);
        Member member = new Member(OauthServerType.GOOGLE, oauthInfo);
        MoodRecordRequest request = MoodRecordRequest.builder()
                .mood(Mood.GOOD)
                .content("content").build();
        MoodRecord moodRecord = new MoodRecord(request,member);
        given(moodRecordQueryRepository.searchMoodRecords(any(String.class),any(String.class))).willReturn(List.of(moodRecord));

        MoodRecordResponse moodRecordResponse = mindTravelService.getRecordMoods(email, "2013-02-03").get(0);

        Assertions.assertThat(moodRecord.getMood()).isEqualTo(moodRecordResponse.getMood());
        Assertions.assertThat(moodRecord.getContent()).isEqualTo(moodRecordResponse.getContent());
    }
}
