package com.jydev.mindtravel.service.mind.travel.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.mind.travel.model.Mood;
import com.jydev.mindtravel.service.mind.travel.model.MoodRecordRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class MoodRecord extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "mood_record_id")
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    public MoodRecord(MoodRecordRequest request, Member member){
        this.content = request.getContent();
        this.mood = request.getMood();
        this.member = member;
    }
}
