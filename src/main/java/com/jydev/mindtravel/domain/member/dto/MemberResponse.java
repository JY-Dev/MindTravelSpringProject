package com.jydev.mindtravel.domain.member.dto;

import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberResponse {
    private Long id;

    private String nickname;

    private String profileImgUrl;
    private MemberRole role;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname() != null ? member.getNickname() : "";
        this.profileImgUrl = member.getProfileImgUrl();
        this.role = member.getRole();
    }
}
