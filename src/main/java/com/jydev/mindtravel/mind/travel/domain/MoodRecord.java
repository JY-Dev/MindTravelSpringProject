package com.jydev.mindtravel.mind.travel.domain;

import com.jydev.mindtravel.base.BaseEntity;
import com.jydev.mindtravel.member.domain.Member;
import com.jydev.mindtravel.mind.travel.model.Mood;
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
}
