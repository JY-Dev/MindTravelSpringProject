package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.RepositoryTest;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.model.*;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostChildCommentCommandRepository;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommandRepository;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommentCommandRepository;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostQueryRepository;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@RepositoryTest
public class MindSharePostQueryRepositoryTest {
    @Autowired
    private MindSharePostQueryRepository repository;

    @Autowired
    private MindSharePostCommandRepository commandRepository;

    @Autowired
    private MemberCommandRepository memberCommandRepository;

    @Autowired
    private MindSharePostCommentCommandRepository commentCommandRepository;

    @Autowired
    private MindSharePostChildCommentCommandRepository childCommentCommandRepository;
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
                .pageOffset(0L)
                .pageSize(5).build();
        List<MindSharePost> result = repository.searchMindSharePosts(request);
        Assertions.assertThat(result.size()).isEqualTo(5);
    }

    @Test
    public void searchMindSharePostsPagingOffsetTest(){
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        saveMindSharePosts(10,category);
        MindSharePostsRequest request = MindSharePostsRequest.builder()
                .category(category)
                .pageOffset(2L)
                .pageSize(5).build();
        List<MindSharePost> result = repository.searchMindSharePosts(request);
        Assertions.assertThat(result.isEmpty()).isEqualTo(true);
    }

    @Test
    public void searchMindSharePostsTotalSizeTest(){
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        saveMindSharePosts(10,category);
        Long result = repository.searchMindSharePostsTotalSize();
        Assertions.assertThat(result).isEqualTo(10);
    }


    @Test
    public void searchMindSharePostCommentFetchJoinTest(){
        MindSharePostRequest request = MindSharePostRequest
                .builder()
                .category(MindSharePostCategory.DAILY)
                .title("title")
                .content("content")
                .build();
        MindSharePost result = commandRepository.save(new MindSharePost(member,request));
        MindSharePostCommentRequest commentRequest = MindSharePostCommentRequest.builder()
                .postId(result.getId())
                .content("content")
                .build();
        List<MindSharePostComment> comments = new ArrayList<>();
        int commentSize = 20;
        for (int i = 0; i < commentSize; i++) {
            comments.add(commentCommandRepository.save(new MindSharePostComment(member,commentRequest)));
        }
        result.addChildComment(comments);
        MindSharePost searchResult = repository.searchMindSharePost(result.getId()).get();
        Assertions.assertThat(searchResult.getComments().size()).isEqualTo(commentSize);
    }

    @Test
    public void searchMindSharePostChildCommentFetchJoinTest(){
        MindSharePostRequest request = MindSharePostRequest
                .builder()
                .category(MindSharePostCategory.DAILY)
                .title("title")
                .content("content")
                .build();
        MindSharePost result = commandRepository.save(new MindSharePost(member,request));
        MindSharePostCommentRequest commentRequest = MindSharePostCommentRequest.builder()
                .postId(result.getId())
                .content("content")
                .build();
        List<MindSharePostComment> comments = new ArrayList<>();
        MindSharePostComment comment = commentCommandRepository.save(new MindSharePostComment(member,commentRequest));
        comments.add(comment);
        result.addChildComment(comments);
        MindSharePostChildCommentRequest childCommentRequest = MindSharePostChildCommentRequest.builder()
                .parentCommentId(comment.getId())
                .content("content")
                .build();
        int commentSize = 20;
        List<MindSharePostChildComment> childComments = new ArrayList<>();
        for (int i = 0; i < commentSize; i++) {
            childComments.add(childCommentCommandRepository.save(new MindSharePostChildComment(member,null,childCommentRequest)));
        }
        comment.addChildComment(childComments);
        MindSharePost searchResult = repository.searchMindSharePost(result.getId()).get();
        MindSharePostComment searchComment = searchResult.getComments().stream().toList().get(0);
        Assertions.assertThat(searchComment.getChildComments().size()).isEqualTo(commentSize);
    }

    private void saveMindSharePosts(int size,MindSharePostCategory category){
        MindSharePostRequest request = MindSharePostRequest
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
