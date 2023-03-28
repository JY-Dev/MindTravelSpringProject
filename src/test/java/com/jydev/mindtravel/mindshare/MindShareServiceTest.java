package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostDetailResponse;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostQueryRepository;
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
    private MindSharePostQueryRepository mindSharePostQueryRepository;
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
        given(mindSharePostQueryRepository.searchMindSharePost(postId)).willReturn(Optional.of(mindSharePost));
        MindSharePostDetailResponse mindSharePostDetailResponse = mindShareService.searchMindSharePost(postId);
        Assertions.assertThat(mindSharePostDetailResponse.getCommentCount()).isEqualTo(size*size+size);
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
        given(mindSharePostQueryRepository.searchMindSharePost(postId)).willReturn(Optional.of(mindSharePost));
        MindSharePostDetailResponse mindSharePostDetailResponse = mindShareService.searchMindSharePost(postId);
        Assertions.assertThat(mindSharePostDetailResponse.getComments().get(0).getChildComments().get(0).getTagNickname()).isEmpty();
    }
}
