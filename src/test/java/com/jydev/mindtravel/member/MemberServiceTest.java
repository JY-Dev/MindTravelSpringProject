package com.jydev.mindtravel.member;

import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.dto.MemberDto;
import com.jydev.mindtravel.domain.member.domain.MemberRole;
import com.jydev.mindtravel.domain.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.domain.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.domain.member.service.MemberService;
import com.jydev.mindtravel.common.exception.BusinessException;
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
    private MemberCommandRepository memberCommandRepository;

    @Mock
    private MemberQueryRepository memberQueryRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void memberLoginWhenNewMemberTest() {
        String email = "test@naver.com";
        String id = "id";
        OauthInfo oauthInfo = new OauthInfo(id, email);
        Member member = new Member(OauthServerType.GOOGLE, oauthInfo);
        MemberDto memberDto = new MemberDto(member);
        doReturn(Optional.empty()).when(memberQueryRepository).findByEmail(email);
        doReturn(member).when(memberCommandRepository).save(any(Member.class));
        MemberDto result = memberService.socialLogin(OauthServerType.GOOGLE, oauthInfo,"");
        Assertions.assertThat(result).isEqualTo(memberDto);
    }

    @Test
    public void memberLoginWhenExistMemberTest() {
        String email = "test@naver.com";
        String id = "id";
        OauthInfo oauthInfo = new OauthInfo(id, email);
        Member member = new Member(OauthServerType.GOOGLE, oauthInfo);
        MemberDto memberDto = new MemberDto(member);
        doReturn(Optional.of(member)).when(memberQueryRepository).findByEmail(email);
        MemberDto result = memberService.socialLogin(OauthServerType.GOOGLE, oauthInfo,"");
        Assertions.assertThat(result).isEqualTo(memberDto);
    }

    @Test
    public void memberEditNicknameTest(){
        String email = "test@naver.com";
        String id = "id";
        OauthInfo oauthInfo = new OauthInfo(id, email);
        Member member = new Member(OauthServerType.GOOGLE, oauthInfo);
        String changeNickname = "changeNickname";
        doReturn(Optional.of(member)).when(memberQueryRepository).findByEmail(email);
        MemberDto result = memberService.editNickname(email, changeNickname);
        Assertions.assertThat(result.getRole()).isEqualTo(MemberRole.USER);
        Assertions.assertThat(result.getNickname()).isEqualTo(changeNickname);
    }

    @Test
    public void memberEditNicknameConflictTest(){
        String email = "test@naver.com";
        String changeNickname = "changeNickname";
        OauthInfo oauthInfo = new OauthInfo("id", email);
        Member member = new Member(OauthServerType.GOOGLE, oauthInfo);
        member.editNickname("Nickname");
        doReturn(Optional.of(member)).when(memberQueryRepository).findByEmail(any());
        doReturn(true).when(memberQueryRepository).isDuplicationNickname(any(),any());
        org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class, () -> memberService.editNickname(email, changeNickname));
    }
}
