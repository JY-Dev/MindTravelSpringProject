package com.jydev.mindtravel.member.model;

import com.jydev.mindtravel.member.domain.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MemberDto {
    private String email;

    private String nickname;

    private MemberRole role;

    public MemberDto(Member member){
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.role = member.getRole();
    }
}
