package com.jydev.mindtravel.domain.member.service;

import com.jydev.mindtravel.common.exception.ConflictException;
import com.jydev.mindtravel.common.exception.InvalidException;
import com.jydev.mindtravel.domain.auth.repository.RefreshTokenRepository;
import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.domain.MemberRole;
import com.jydev.mindtravel.domain.member.dto.MemberDto;
import com.jydev.mindtravel.domain.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.domain.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberCommandRepository commandRepository;
    private final MemberQueryRepository queryRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public MemberDto socialLogin(OauthServerType type, OauthInfo info, String fcmToken) {
        Member member = queryRepository.findByEmail(info.getEmail())
                .orElseGet(() -> commandRepository.save(new Member(type, info)));
        member.login(fcmToken);
        return new MemberDto(member);
    }

    public void logout(String email) {
        Member member = MemberUtils.findMemberByEmail(queryRepository, email);
        refreshTokenRepository.deleteRefreshToken(email);
        member.logout();
    }

    @Transactional(readOnly = true)
    public MemberDto findMemberByEmail(String email) {
        return new MemberDto(MemberUtils.findMemberByEmail(queryRepository, email));
    }

    public MemberDto editNickname(String email, String nickname) {
        Member member = MemberUtils.findMemberByEmail(queryRepository, email);
        member.updateRole(MemberRole.USER);
        validationDuplicationNickname(email, nickname, member);
        member.editNickname(nickname);
        return new MemberDto(member);
    }

    private void validationDuplicationNickname(String email, String nickname, Member member) {
        if (member.getNickname() != null && !member.getNickname().equals(nickname)) {
            if (nickname.isEmpty() || nickname.length() > 16)
                throw new InvalidException(InvalidException.MEMBER_NICKNAME);
            if (queryRepository.isDuplicationNickname(email, nickname))
                throw new ConflictException(ConflictException.MEMBER);
        }
    }

}
