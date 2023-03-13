package com.jydev.mindtravel.member.model;

import com.jydev.mindtravel.member.domain.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MemberDto {
    private Long memberIdx;
    private String email;

    private String nickname;

    private String profileImgUrl;
    private MemberRole role;

    public MemberDto(Member member){
        this.memberIdx = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImgUrl = member.getProfileImgUrl();
        this.role = member.getRole();
    }
}
