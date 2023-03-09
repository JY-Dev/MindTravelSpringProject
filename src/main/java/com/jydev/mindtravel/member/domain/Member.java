package com.jydev.mindtravel.member.domain;

import com.jydev.mindtravel.base.BaseEntity;
import com.jydev.mindtravel.member.model.MemberRole;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private OauthServerType oauthServerType;

    private String oauthId;

    private String profileImage = "";

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    public Member(OauthServerType type , OauthInfo info){
        this.oauthServerType = type;
        this.email = info.getEmail();
        this.oauthId = info.getId();
        this.role = MemberRole.USER;
    }
}
