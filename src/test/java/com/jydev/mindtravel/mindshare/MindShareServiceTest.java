package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.service.exception.ClientException;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostDetailResponse;
import com.jydev.mindtravel.service.mind.share.repository.*;
import com.jydev.mindtravel.service.mind.share.service.MindShareService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        org.junit.jupiter.api.Assertions.assertThrows(ClientException.class, () -> mindShareService.deletePostComment(0L, requestMemberId));
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
        org.junit.jupiter.api.Assertions.assertThrows(ClientException.class, () -> mindShareService.deletePostChildComment(0L, requestMemberId));
    }

    @Test
    public void insertChildCommentWhenHasTagMemberTest() {
        Member requestMember = mock(Member.class);
        MindSharePostChildCommentRequest request = MindSharePostChildCommentRequest.builder()
                .parentCommentId(0L)
                .tagMemberId(1L)
                .content("content")
                .build();
        given(memberCommandRepository.findById(any(Long.class))).willReturn(Optional.of(requestMember));
        mindShareService.insertPostChildComment(request, 0L);
        verify(memberCommandRepository, times(2)).findById(any(Long.class));
    }

    @Test
    public void insertChildCommentWhenNotHasTagMemberTest() {
        Member requestMember = mock(Member.class);
        MindSharePostChildCommentRequest request = MindSharePostChildCommentRequest.builder()
                .parentCommentId(0L)
                .tagMemberId(-1L)
                .content("content")
                .build();
        given(memberCommandRepository.findById(any(Long.class))).willReturn(Optional.of(requestMember));
        mindShareService.insertPostChildComment(request, 0L);
        verify(memberCommandRepository, times(1)).findById(any(Long.class));
    }
}
