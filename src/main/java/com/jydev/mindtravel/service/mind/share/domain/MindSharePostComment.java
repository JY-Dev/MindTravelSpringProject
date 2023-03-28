package com.jydev.mindtravel.service.mind.share.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCommentRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@DynamicUpdate
@NoArgsConstructor
@ToString
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
    private Set<MindSharePostChildComment> childComments = new HashSet<>();

    public MindSharePostComment(Member member,MindSharePostCommentRequest request){
        this.member = member;
        this.postId = request.getPostId();
        this.content = request.getContent();
        this.isDeleted = false;
    }

    public void addChildComment(Collection<MindSharePostChildComment> comments){
        this.childComments.addAll(comments);
    }
}