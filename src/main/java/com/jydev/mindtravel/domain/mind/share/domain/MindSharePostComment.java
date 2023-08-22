package com.jydev.mindtravel.domain.mind.share.domain;

import com.jydev.mindtravel.common.base.BaseEntity;
import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentEditRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class MindSharePostComment extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "post_id")
    private Long postId;
    private String content;
    private Boolean isDeleted;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent_comment_id")
    private final List<MindSharePostChildComment> childComments = new ArrayList<>();

    public MindSharePostComment(Member member, MindSharePostCommentRequest request) {
        this.member = member;
        this.postId = request.getPostId();
        this.content = request.getContent();
        this.isDeleted = false;
    }

    public boolean isCreator(Long memberId) {
        return this.member.getId().equals(memberId);
    }

    public void editComment(MindSharePostCommentEditRequest request) {
        this.content = request.getContent();
    }

    public void delete() {
        isDeleted = true;
    }

    public void addChildComment(Collection<MindSharePostChildComment> comments) {
        this.childComments.addAll(comments);
    }
}
