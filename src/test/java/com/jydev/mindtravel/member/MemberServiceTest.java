package com.jydev.mindtravel.member;

import com.jydev.mindtravel.member.domain.Member;
import com.jydev.mindtravel.member.model.MemberDto;
import com.jydev.mindtravel.member.repository.MemberRepository;
import com.jydev.mindtravel.member.service.MemberService;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks

    private MemberService memberService;

    @Test
    public void memberLoginWhenNewMemberTest() {
        String email = "test@naver.com";
        String id = "id";
        OauthInfo oauthInfo = new OauthInfo(id, email);
        Member member = new Member(OauthServerType.GOOGLE, oauthInfo);
        MemberDto memberDto = new MemberDto(member);
        doReturn(Optional.empty()).when(memberRepository).findByEmail(email);
        doReturn(member).when(memberRepository).save(any(Member.class));
        MemberDto result = memberService.socialLogin(OauthServerType.GOOGLE, oauthInfo);
        Assertions.assertThat(result).isEqualTo(memberDto);
    }

    @Test
    public void memberLoginWhenExistMemberTest() {
        String email = "test@naver.com";
        String id = "id";
        OauthInfo oauthInfo = new OauthInfo(id, email);
        Member member = new Member(OauthServerType.GOOGLE, oauthInfo);
        MemberDto memberDto = new MemberDto(member);
        doReturn(Optional.of(member)).when(memberRepository).findByEmail(email);
        MemberDto result = memberService.socialLogin(OauthServerType.GOOGLE, oauthInfo);
        Assertions.assertThat(result).isEqualTo(memberDto);
    }
}
