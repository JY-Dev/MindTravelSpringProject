package com.jydev.mindtravel.service.mind.share.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import com.jydev.mindtravel.service.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class MindSharePostLike extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @Column(name = "post_id")
    private Long postId;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MindSharePostLike(Long postId, Member member){
        this.postId = postId;
        this.member = member;
    }
}
