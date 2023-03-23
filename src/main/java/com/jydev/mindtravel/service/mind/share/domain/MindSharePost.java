package com.jydev.mindtravel.service.mind.share.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import com.jydev.mindtravel.service.mind.share.model.WriteMindSharePostRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@DynamicUpdate
@NoArgsConstructor
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
    private Long likeCount;
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MindSharePost(Member member, WriteMindSharePostRequest request){
        this.category = request.getCategory();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.likeCount = 0L;
        this.viewCount = 0L;
        this.member = member;
    }
}
