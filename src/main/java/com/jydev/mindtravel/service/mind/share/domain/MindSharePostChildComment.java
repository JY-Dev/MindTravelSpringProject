package com.jydev.mindtravel.service.mind.share.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentEditRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class MindSharePostChildComment extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "tag_member_id")
    private Member tagMember;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    private String content;

    public MindSharePostChildComment(Member member, Member tagMember,MindSharePostChildCommentRequest request){
        this.member = member;
        this.tagMember = tagMember;
        this.parentCommentId = request.getParentCommentId();
        this.content = request.getContent();
    }

    public boolean isCreator(Long memberId){
        return this.member.getId().equals(memberId);
    }

    public void editComment(MindSharePostCommentEditRequest request){
        this.content = request.getContent();
    }
}
