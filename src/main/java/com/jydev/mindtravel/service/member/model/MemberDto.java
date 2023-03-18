package com.jydev.mindtravel.service.member.model;

import com.jydev.mindtravel.service.member.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long memberIdx;
    private String email;

    private String nickname;

    private String profileImgUrl;
    private MemberRole role;

    private LocalDateTime createdDate;

    public MemberDto(Member member){
        this.memberIdx = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImgUrl = member.getProfileImgUrl();
        this.role = member.getRole();
        this.createdDate = member.getCreatedDate();
    }
}
