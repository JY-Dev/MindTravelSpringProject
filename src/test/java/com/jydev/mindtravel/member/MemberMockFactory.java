package com.jydev.mindtravel.member;

import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;

public class MemberMockFactory {
    public static Member getMember(){
        String email = "test@naver.com";
        String id = "id";
        OauthInfo oauthInfo = new OauthInfo(id, email);
        return new Member(OauthServerType.GOOGLE,oauthInfo);
    }
}
