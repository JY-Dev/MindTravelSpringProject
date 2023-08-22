package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.domain.mind.share.event.MindSharePostCommentFcmEvent;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MindSharePostCommentFcmEventTest {

    @Mock
    Member writermember;

    @Mock
    MindSharePost post;

    @Mock
    MindSharePostComment comment;

    @Mock
    MindSharePostCommentRequest commentRequest;

    @Mock
    MindSharePostChildCommentRequest childCommentRequest;

    @BeforeEach
    void init(){
        Long writerId = 1L;
        given(writermember.getId()).willReturn(writerId);

    }
    @Test
    public void hasCommentEventTestWhenPostWriterEqualCommentWriter(){
        given(post.getMember()).willReturn(writermember);
        MindSharePostCommentFcmEvent event = new MindSharePostCommentFcmEvent(commentRequest, writermember);

        boolean hasCommentEvent = event.hasCommentEvent(post);

        Assertions.assertThat(hasCommentEvent).isFalse();
    }

    @Test
    public void hasChildCommentEventTestWhenPostWriterEqualCommentWriter(){
        given(comment.getMember()).willReturn(writermember);
        MindSharePostCommentFcmEvent event = new MindSharePostCommentFcmEvent(childCommentRequest, writermember,null);

        boolean hasChildComment = event.hasChildCommentEvent(comment);

        Assertions.assertThat(hasChildComment).isFalse();
    }

    @Test
    public void hasTagMemberEventTestWhenPostWriterEqualCommentWriter(){
        MindSharePostCommentFcmEvent event = new MindSharePostCommentFcmEvent(childCommentRequest, writermember, writermember);

        boolean hasChildComment = event.hasTagMemberEvent();

        Assertions.assertThat(hasChildComment).isFalse();
    }

    @Test
    public void hasCommentEventTestWhenPostWriterNotEqualCommentWriter(){
        Long postWriterId = 2L;
        Member postWriter = mock(Member.class);
        given(postWriter.getId()).willReturn(postWriterId);
        given(post.getMember()).willReturn(postWriter);
        MindSharePostCommentFcmEvent event = new MindSharePostCommentFcmEvent(commentRequest, writermember);

        boolean hasCommentEvent = event.hasCommentEvent(post);

        Assertions.assertThat(hasCommentEvent).isTrue();
    }

    @Test
    public void hasChildCommentEventTestWhenPostWriterNotEqualCommentWriter(){
        Long parentCommentWriterId = 2L;
        Member parentCommentWriter = mock(Member.class);
        given(parentCommentWriter.getId()).willReturn(parentCommentWriterId);
        given(comment.getMember()).willReturn(parentCommentWriter);
        MindSharePostCommentFcmEvent event = new MindSharePostCommentFcmEvent(childCommentRequest,writermember,null);

        boolean hasChildComment = event.hasChildCommentEvent(comment);

        Assertions.assertThat(hasChildComment).isTrue();
    }

    @Test
    public void hasTagMemberEventTestWhenPostWriterNotEqualCommentWriter(){
        Long tagMemberId = 2L;
        Member tagMember = mock(Member.class);
        given(tagMember.getId()).willReturn(tagMemberId);
        MindSharePostCommentFcmEvent event = new MindSharePostCommentFcmEvent(childCommentRequest ,writermember, tagMember);

        boolean hasChildComment = event.hasTagMemberEvent();

        Assertions.assertThat(hasChildComment).isTrue();
    }
}
