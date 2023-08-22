package com.jydev.mindtravel.domain.mind.share.event;

import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.dto.MemberFcmDto;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentRequest;
import lombok.Getter;

@Getter
public class MindSharePostCommentFcmEvent {

    private final Long parentCommentId;
    private final String content;
    private final Long postId;
    private final MemberFcmDto commentWriterMember;
    private MemberFcmDto tagMember;

    public MindSharePostCommentFcmEvent(MindSharePostCommentRequest request, Member commentWriterMember) {
        this.postId = request.getPostId();
        this.content = request.getContent();
        this.parentCommentId = -1L;
        this.commentWriterMember = new MemberFcmDto(commentWriterMember);
    }

    public MindSharePostCommentFcmEvent(MindSharePostChildCommentRequest request, Member commentWriterMember, Member tagMember) {
        this.content = request.getContent();
        this.postId = request.getPostId();
        this.commentWriterMember = new MemberFcmDto(commentWriterMember);
        this.parentCommentId = request.getParentCommentId();
        if (tagMember != null)
            this.tagMember = new MemberFcmDto(tagMember);
    }

    public boolean hasCommentEvent(MindSharePost post) {
        return !commentWriterMember.getMemberIdx().equals(post.getMember().getId());
    }

    public boolean hasChildCommentEvent(MindSharePostComment parentComment) {
        return !commentWriterMember.getMemberIdx().equals(parentComment.getMember().getId());
    }

    public boolean hasTagMemberEvent() {
        return tagMember != null &&
                !commentWriterMember.getMemberIdx().equals(tagMember.getMemberIdx());
    }
}
