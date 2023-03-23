package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.RepositoryTest;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostsRequest;
import com.jydev.mindtravel.service.mind.share.model.WriteMindSharePostRequest;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommandRepository;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostQueryRepository;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RepositoryTest
public class MindSharePostQueryRepositoryTest {
    @Autowired
    private MindSharePostQueryRepository repository;

    @Autowired
    private MindSharePostCommandRepository commandRepository;

    @Autowired
    private MemberCommandRepository memberCommandRepository;
    private Member member;
    @BeforeEach
    public void init(){
        String email = "test@naver.com";
        String id = "id";
        OauthInfo oauthInfo = new OauthInfo(id, email);
        member = new Member(OauthServerType.GOOGLE, oauthInfo);
        memberCommandRepository.save(member);
    }

    @Test
    public void searchMindSharePostsPagingTest(){
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        saveMindSharePosts(10,category);
        MindSharePostsRequest request = MindSharePostsRequest.builder()
                .category(category)
                .pageOffset(0)
                .pageSize(5).build();
        List<MindSharePost> result = repository.searchMindSharePosts(request);
        Assertions.assertThat(result.size()).isEqualTo(5);
    }

    @Test
    public void searchMindSharePostsTotalSizeTest(){
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        saveMindSharePosts(10,category);
        Long result = repository.searchMindSharePostsTotalSize();
        Assertions.assertThat(result).isEqualTo(10);
    }

    private void saveMindSharePosts(int size,MindSharePostCategory category){
        WriteMindSharePostRequest request = WriteMindSharePostRequest
                .builder()
                .category(category)
                .title("title")
                .content("content")
                .build();
        for (int i = 0; i < size; i++) {
            MindSharePost post = new MindSharePost(member,request);
            commandRepository.save(post);
        }
    }
}
