package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.RepositoryTest;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostLike;
import com.jydev.mindtravel.service.mind.share.model.*;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostRequest;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostResponse;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostsRequest;
import com.jydev.mindtravel.service.mind.share.repository.*;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private MindSharePostLikeCommandRepository likeCommandRepository;

    private Member member;

    @Autowired
    private EntityManager entityManager;
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
        Long result = repository.searchMindSharePostsTotalSize(category);
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
        entityManager.flush();
        entityManager.clear();
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
        MindSharePostChildCommentRequest childCommentRequest = getMindSharePostChildCommentRequest(comment.getId());
        int commentSize = 20;
        for (int i = 0; i < commentSize; i++) {
            childCommentCommandRepository.save(new MindSharePostChildComment(member, null, childCommentRequest));
        }
        entityManager.flush();
        entityManager.clear();
        MindSharePost searchResult = repository.searchMindSharePost(post.getId()).get();
        MindSharePostComment searchComment = searchResult.getComments().stream().toList().get(0);
        Assertions.assertThat(searchComment.getChildComments().size()).isEqualTo(commentSize);
    }

    @Test
    public void searchMindSharePostLikesFetchJoinTest() {
        MindSharePostRequest request = getMindSharePostRequest(MindSharePostCategory.DAILY);
        MindSharePost post = commandRepository.save(new MindSharePost(member, request));
        Long postId = post.getId();
        likeCommandRepository.save(new MindSharePostLike(postId,member));
        entityManager.flush();
        entityManager.clear();
        MindSharePost result = repository.searchMindSharePost(postId).get();
        Assertions.assertThat(result.getLikes().size()).isEqualTo(1);
    }

    @Test
    public void searchMindSharePostsCommentSizeTest() {
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        MindSharePost post = commandRepository.save(new MindSharePost(member, getMindSharePostRequest(category)));
        List<MindSharePostComment> comments = getMindSharePostComments(member, post.getId(), 1);
        MindSharePostComment comment = commentCommandRepository.save(comments.get(0));
        int commentSize = 20;
        List<MindSharePostChildComment> childComments = getMindSharePostChildComments(member, comment.getId(), commentSize);
        for (int i = 0; i < commentSize; i++) {
            childCommentCommandRepository.save(childComments.get(i));
        }
        entityManager.flush();
        entityManager.clear();
        MindSharePostsRequest postsRequest = getMindSharePostsRequest(category);
        MindSharePostResponse searchResult = repository.searchMindSharePosts(postsRequest).get(0);
        Assertions.assertThat(searchResult.getCommentCount()).isEqualTo(commentSize + 1);
    }

    @Test
    public void searchMindSharePostIncreaseViewCountTest() {
        MindSharePostRequest request = getMindSharePostRequest(MindSharePostCategory.DAILY);
        MindSharePost post = commandRepository.save(new MindSharePost(member, request));
        repository.increaseViewCount(post.getId());
        entityManager.flush();
        entityManager.clear();
        MindSharePost result = repository.searchMindSharePost(post.getId()).get();
        Assertions.assertThat(result.getViewCount()).isEqualTo(1);
    }

    @Test
    public void deleteMindSharePostCommentTest(){
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        MindSharePost post = commandRepository.save(new MindSharePost(member, getMindSharePostRequest(category)));
        List<MindSharePostComment> comments = getMindSharePostComments(member, post.getId(), 1);
        MindSharePostComment comment = commentCommandRepository.save(comments.get(0));
        repository.deleteMindSharePostComment(comment.getId());
        entityManager.flush();
        entityManager.clear();
        MindSharePostComment result = commentCommandRepository.findById(comment.getId()).get();
        Assertions.assertThat(result.getIsDeleted()).isTrue();
    }

    @Test
    public void getMindSharePostLikesTest(){
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        Long postId = commandRepository.save(new MindSharePost(member, getMindSharePostRequest(category))).getId();
        likeCommandRepository.save(new MindSharePostLike(postId, member));
        List<MindSharePostLike> postLikes = repository.getPostLikes(postId);
        Assertions.assertThat(postLikes.size()).isEqualTo(1);
    }

    @Test
    public void getMindSharePostCommentTest() {
        MindSharePostCategory category = MindSharePostCategory.DAILY;
        MindSharePostRequest request = getMindSharePostRequest(category);
        Long postId = commandRepository.save(new MindSharePost(member, request)).getId();
        List<MindSharePostComment> comments = getMindSharePostComments(member, postId, 1);
        MindSharePostComment comment = commentCommandRepository.save(comments.get(0));
        MindSharePostChildCommentRequest childCommentRequest = getMindSharePostChildCommentRequest(comment.getId());
        int commentSize = 20;
        for (int i = 0; i < commentSize; i++) {
            childCommentCommandRepository.save(new MindSharePostChildComment(member, null, childCommentRequest));
        }
        entityManager.flush();
        entityManager.clear();
        List<MindSharePostComment> searchComments = repository.getPostComments(postId);
        Assertions.assertThat(searchComments.size()).isEqualTo(1);
        Assertions.assertThat(searchComments.get(0).getChildComments().size()).isEqualTo(commentSize);
    }

    private void saveMindSharePosts(int size, MindSharePostCategory category) {
        MindSharePostRequest request = getMindSharePostRequest(category);
        for (int i = 0; i < size; i++) {
            MindSharePost post = new MindSharePost(member, request);
            commandRepository.save(post);
        }
    }
}
