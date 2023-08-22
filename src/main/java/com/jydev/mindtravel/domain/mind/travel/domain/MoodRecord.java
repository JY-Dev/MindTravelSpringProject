package com.jydev.mindtravel.domain.mind.travel.domain;

import com.jydev.mindtravel.common.base.BaseEntity;
import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordRequest;
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MoodRecord(MoodRecordRequest request, Member member) {
        this.content = request.getContent();
        this.mood = request.getMood();
        this.member = member;
    }
}
