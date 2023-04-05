package com.jydev.mindtravel.service.mind.share.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class MindSharePost extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private MindSharePostCategory category;
    private String title;
    private String content;
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    @OrderBy("createdDate ASC")
    private Set<MindSharePostComment> comments = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    @OrderBy("createdDate DESC")
    private Set<MindSharePostLike> likes = new HashSet<>();

    public MindSharePost(Member member, MindSharePostRequest request){
        this.category = request.getCategory();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.viewCount = 0L;
        this.member = member;
    }

    public void addChildComment(Collection<MindSharePostComment> comments){
        this.comments.addAll(comments);
    }
}
