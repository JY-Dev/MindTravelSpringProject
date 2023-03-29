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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jydev.mindtravel.member.MemberMockFactory.getMember;
import static com.jydev.mindtravel.mindshare.MindShareMockFactory.*;

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
    public void init() {
        member = memberCommandRepository.save(getMember());
    }

    @Test
    public void searchMindSharePostsPagingTest() {
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        saveMindSharePosts(10, category);
        MindSharePostsRequest request = MindSharePostsRequest.builder()
                .category(category)
                .pageOffset(0L)
                .pageSize(5).build();
        List<MindSharePostResponse> result = repository.searchMindSharePosts(request);
        Assertions.assertThat(result.size()).isEqualTo(5);
    }

    @Test
    public void searchMindSharePostsPagingOffsetTest() {
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        saveMindSharePosts(10, category);
        MindSharePostsRequest request = MindSharePostsRequest.builder()
                .category(category)
                .pageOffset(2L)
                .pageSize(5).build();
        List<MindSharePostResponse> result = repository.searchMindSharePosts(request);
        Assertions.assertThat(result.isEmpty()).isEqualTo(true);
    }

    @Test
    public void searchMindSharePostsTotalSizeTest() {
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        saveMindSharePosts(10, category);
        Long result = repository.searchMindSharePostsTotalSize();
        Assertions.assertThat(result).isEqualTo(10);
    }


    @Test
    public void searchMindSharePostCommentFetchJoinTest() {
        MindSharePostRequest request = getMindSharePostRequest(MindSharePostCategory.DAILY);
        MindSharePost post = commandRepository.save(new MindSharePost(member, request));
        int commentSize = 20;
        List<MindSharePostComment> comments = getMindSharePostComments(member, post.getId(), commentSize);
        for (MindSharePostComment comment : comments) {
            commentCommandRepository.save(comment);
        }
        post.addChildComment(comments);
        MindSharePost searchResult = repository.searchMindSharePost(post.getId()).get();
        Assertions.assertThat(searchResult.getComments().size()).isEqualTo(commentSize);
    }

    @Test
    public void searchMindSharePostChildCommentFetchJoinTest() {
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        MindSharePostRequest request = getMindSharePostRequest(category);
        MindSharePost post = commandRepository.save(new MindSharePost(member, request));
        List<MindSharePostComment> comments = getMindSharePostComments(member, post.getId(), 1);
        MindSharePostComment comment = commentCommandRepository.save(comments.get(0));
        comments.add(comment);
        post.addChildComment(comments);

        MindSharePostChildCommentRequest childCommentRequest = getMindSharePostChildCommentRequest(comment.getId());
        int commentSize = 20;
        List<MindSharePostChildComment> childComments = new ArrayList<>();
        for (int i = 0; i < commentSize; i++) {
            childComments.add(childCommentCommandRepository.save(new MindSharePostChildComment(member, null, childCommentRequest)));
        }
        comment.addChildComment(childComments);
        MindSharePost searchResult = repository.searchMindSharePost(post.getId()).get();
        MindSharePostComment searchComment = searchResult.getComments().stream().toList().get(0);
        Assertions.assertThat(searchComment.getChildComments().size()).isEqualTo(commentSize);
    }

    @Test
    public void searchMindSharePostsCommentSizeTest() {
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        MindSharePost post = commandRepository.save(new MindSharePost(member, getMindSharePostRequest(category)));
        List<MindSharePostComment> comments = getMindSharePostComments(member, post.getId(), 1);
        MindSharePostComment comment = commentCommandRepository.save(comments.get(0));
        post.addChildComment(comments);
        int commentSize = 20;
        List<MindSharePostChildComment> childComments = getMindSharePostChildComments(member, comment.getId(), commentSize);
        for (int i = 0; i < commentSize; i++) {
            childCommentCommandRepository.save(childComments.get(i));
        }
        comment.addChildComment(childComments);
        MindSharePostsRequest postsRequest = getMindSharePostsRequest(category);
        MindSharePostResponse searchResult = repository.searchMindSharePosts(postsRequest).get(0);
        Assertions.assertThat(searchResult.getCommentCount()).isEqualTo(commentSize + 1);
    }

    @Test
    public void searchMindSharePostIncreaseViewCountTest() {
        MindSharePostRequest request = getMindSharePostRequest(MindSharePostCategory.DAILY);
        MindSharePost post = commandRepository.save(new MindSharePost(member, request));
        repository.increaseViewCount(post.getId());
        MindSharePost result = repository.searchMindSharePost(post.getId()).get();
        Assertions.assertThat(result.getViewCount()).isEqualTo(1);
    }

    private void saveMindSharePosts(int size, MindSharePostCategory category) {
        MindSharePostRequest request = getMindSharePostRequest(category);
        for (int i = 0; i < size; i++) {
            MindSharePost post = new MindSharePost(member, request);
            commandRepository.save(post);
        }
    }
}
