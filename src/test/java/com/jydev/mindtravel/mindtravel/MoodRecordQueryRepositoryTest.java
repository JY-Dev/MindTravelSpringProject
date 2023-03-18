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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @BeforeEach
    void init(){
        OauthInfo oauthInfo = new OauthInfo("id", "email");
        memberService.socialLogin(OauthServerType.GOOGLE, oauthInfo);
        MoodRecordRequest moodRecordRequest = MoodRecordRequest.builder()
                .mood(Mood.GOOD)
                .content("컨텐츠").build();
        mindTravelService.recordMood("email",moodRecordRequest);
    }
    @Test
    public void searchMoodRecordsTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<MoodRecord> email = repository.searchMoodRecords("email", formatter.format(LocalDateTime.now()));
        System.out.println(email.get(0).getId());
    }

    @Test
    public void findMoodRecord(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<MoodRecord> email = repository.searchMoodRecords("email", formatter.format(LocalDateTime.now()));
        Long id = email.get(0).getId();
        MoodRecord result = repository.findMoodRecord("email", id);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(id);
    }
}