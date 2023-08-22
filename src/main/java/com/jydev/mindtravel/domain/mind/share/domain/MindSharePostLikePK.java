package com.jydev.mindtravel.domain.mind.share.domain;

import com.jydev.mindtravel.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class MindSharePostLikePK implements Serializable {
    private Long postId;
    private Member member;
}
