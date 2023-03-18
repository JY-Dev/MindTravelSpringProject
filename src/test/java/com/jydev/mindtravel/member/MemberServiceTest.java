package com.jydev.mindtravel.member;

import com.jydev.mindtravel.member.domain.Member;
import com.jydev.mindtravel.member.model.MemberDto;
import com.jydev.mindtravel.member.model.MemberRole;
import com.jydev.mindtravel.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.member.service.MemberService;
import com.jydev.mindtravel.web.http.HttpErrorException;
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
        doReturn(Optional.of(member)).when(memberQueryRepository).findByEmail(email);
        MemberDto result = memberService.socialLogin(OauthServerType.GOOGLE, oauthInfo);
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
        doReturn(false).when(memberQueryRepository).isDuplicationNickname(email,changeNickname);
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
        doReturn(Optional.of(member)).when(memberQueryRepository).findByEmail(email);
        doReturn(true).when(memberQueryRepository).isDuplicationNickname(email,changeNickname);
        org.junit.jupiter.api.Assertions.assertThrows(HttpErrorException.class, () -> memberService.editNickname(email, changeNickname));
    }
}
