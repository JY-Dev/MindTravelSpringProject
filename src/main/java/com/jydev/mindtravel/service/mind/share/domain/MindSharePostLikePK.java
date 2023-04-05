package com.jydev.mindtravel.service.mind.share.domain;

import com.jydev.mindtravel.service.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class MindSharePostLikePK implements Serializable {
    private Long postId;
    private Member member;
}
