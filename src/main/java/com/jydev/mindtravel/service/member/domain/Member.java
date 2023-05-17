package com.jydev.mindtravel.service.member.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import com.jydev.mindtravel.service.member.model.MemberRole;
import com.jydev.mindtravel.service.mind.travel.domain.MoodRecord;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true,nullable = false)
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

    public Member(OauthServerType type , OauthInfo info){
        this.oauthServerType = type;
        this.email = info.getEmail();
        this.oauthId = info.getId();
        this.role = MemberRole.NEW_USER;
        this.nickname = null;
        this.profileImgUrl = "";
    }

    public void editNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateRole(MemberRole role){
        this.role = role;
    }

    public void updateFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }
}
