package com.jydev.mindtravel.mindtravel;

import com.jydev.mindtravel.service.member.service.MemberService;
import com.jydev.mindtravel.service.mind.travel.domain.MoodRecord;
import com.jydev.mindtravel.service.mind.travel.model.Mood;
import com.jydev.mindtravel.service.mind.travel.model.MoodRecordRequest;
import com.jydev.mindtravel.service.mind.travel.repository.MoodRecordQueryRepository;
import com.jydev.mindtravel.service.mind.travel.service.MindTravelService;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
public class MoodRecordQueryRepositoryTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MindTravelService mindTravelService;

    @Autowired
    private MoodRecordQueryRepository repository;

    @Test
    public void searchMoodRecordsTest() {
        OauthInfo oauthInfo = new OauthInfo("id", "email");
        memberService.socialLogin(OauthServerType.GOOGLE, oauthInfo);
        MoodRecordRequest moodRecordRequest = MoodRecordRequest.builder()
                .mood(Mood.GOOD)
                .content("컨텐츠").build();
        mindTravelService.recordMood("email",moodRecordRequest);
        List<MoodRecord> email = repository.searchMoodRecords("email", "2023-03-18");
        System.out.println(email.get(0).getMood());
    }
}
