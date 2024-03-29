package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostDetailResponse;
import com.jydev.mindtravel.domain.mind.share.repository.MindSharePostChildCommentCommandRepository;
import com.jydev.mindtravel.domain.mind.share.repository.MindSharePostCommentCommandRepository;
import com.jydev.mindtravel.domain.mind.share.repository.MindSharePostQueryRepository;
import com.jydev.mindtravel.domain.mind.share.service.MindShareService;
import com.jydev.mindtravel.common.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MindShareServiceTest {
    @Mock
    private MemberCommandRepository memberCommandRepository;
    @Mock
    private MindSharePostCommentCommandRepository commentCommandRepository;
    @Mock
    private MindSharePostChildCommentCommandRepository childCommentCommandRepository;
    @Mock
    private MindSharePostQueryRepository postQueryRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @InjectMocks
    private MindShareService mindShareService;

    @Test
    public void searchMindSharePostCommentCountTest() {
        MindSharePost mindSharePost = mock(MindSharePost.class);
        Member member = mock(Member.class);
        long postId = 1L;
        int size = 10;
        given(mindSharePost.getComments()).
                willReturn(new HashSet<>(MindShareMockFactory.getMindSharePostCommentsWithChildComments(member, postId, size)));
        given(mindSharePost.getMember()).willReturn(member);
        given(postQueryRepository.searchMindSharePost(postId)).willReturn(Optional.of(mindSharePost));
        MindSharePostDetailResponse mindSharePostDetailResponse = mindShareService.searchMindSharePost(postId);
        Assertions.assertThat(mindSharePostDetailResponse.getCommentCount()).isEqualTo(size * size + size);
    }

    @Test
    public void searchMindSharePostChildCommentWhenTagMemberNullTest() {
        MindSharePost mindSharePost = mock(MindSharePost.class);
        Member member = mock(Member.class);
        long postId = 1L;
        int size = 10;
        given(mindSharePost.getComments()).
                willReturn(new HashSet<>(MindShareMockFactory.getMindSharePostCommentsWithChildComments(member, postId, size)));
        given(mindSharePost.getMember()).willReturn(member);
        given(postQueryRepository.searchMindSharePost(postId)).willReturn(Optional.of(mindSharePost));
        MindSharePostDetailResponse mindSharePostDetailResponse = mindShareService.searchMindSharePost(postId);
        Assertions.assertThat(mindSharePostDetailResponse.getComments().get(0).getChildComments().get(0).getTagNickname()).isEmpty();
    }

    @Test
    public void deletePostCommentWhenIsNotCommentCreatorTest() {
        Member commentCreatorMember = mock(Member.class);
        MindSharePostCommentRequest request = MindSharePostCommentRequest.builder()
                .postId(0L).content("content").build();
        MindSharePostComment comment = new MindSharePostComment(commentCreatorMember, request);
        long requestMemberId = 0L;
        long commentCreatorId = 1L;
        given(commentCreatorMember.getId()).willReturn(commentCreatorId);
        given(commentCommandRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
        org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class, () -> mindShareService.deletePostComment(0L, requestMemberId));
    }

    @Test
    public void deletePostChildCommentWhenIsNotCommentCreatorTest() {
        Member commentCreatorMember = mock(Member.class);
        MindSharePostChildCommentRequest request = MindSharePostChildCommentRequest.builder()
                .parentCommentId(0L)
                .tagMemberId(-1L)
                .content("content")
                .build();
        MindSharePostChildComment comment = new MindSharePostChildComment(commentCreatorMember, null, request);
        long requestMemberId = 0L;
        long commentCreatorId = 1L;
        given(commentCreatorMember.getId()).willReturn(commentCreatorId);
        given(childCommentCommandRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
        org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class, () -> mindShareService.deletePostChildComment(0L, requestMemberId));
    }
}
