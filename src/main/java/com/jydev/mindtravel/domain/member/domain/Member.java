package com.jydev.mindtravel.domain.member.domain;

import com.jydev.mindtravel.common.base.BaseEntity;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(indexes = @Index(name = "index_email", columnList = "email"))
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private OauthServerType oauthServerType;

    private String oauthId;

    private String profileImgUrl;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    private String fcmToken;

    public Member(OauthServerType type, OauthInfo info) {
        this.oauthServerType = type;
        this.email = info.getEmail();
        this.oauthId = info.getId();
        this.role = MemberRole.NEW_USER;
        this.nickname = null;
        this.profileImgUrl = "";
    }

    public void editNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateRole(MemberRole role) {
        this.role = role;
    }

    public void login(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void logout() {
        this.fcmToken = "";
    }
}
