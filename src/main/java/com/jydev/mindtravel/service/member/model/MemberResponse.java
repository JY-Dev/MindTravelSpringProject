package com.jydev.mindtravel.service.member.model;

import com.jydev.mindtravel.service.member.domain.Member;
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

    public MemberResponse(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname() != null ? member.getNickname() : "";
        this.profileImgUrl = member.getProfileImgUrl();
        this.role = member.getRole();
    }
}
