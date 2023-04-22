package com.jydev.mindtravel.service.member.service;

import com.jydev.mindtravel.auth.repository.RefreshTokenRepository;
import com.jydev.mindtravel.service.exception.ClientException;
import com.jydev.mindtravel.service.exception.ConflictException;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.member.model.MemberRole;
import com.jydev.mindtravel.service.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.service.member.repository.MemberQueryRepository;
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
        member.updateFcmToken(fcmToken);
        return new MemberDto(member);
    }

    public void logout(String email){
        Member member = queryRepository.findByEmail(email)
                        .orElseThrow(() -> new ClientException("멤버 검색 실패"));
        refreshTokenRepository.deleteRefreshToken(email);
        member.updateFcmToken("");
    }

    public MemberDto findMemberByEmail(String email) {
        return queryRepository.findByEmail(email)
                .map(MemberDto::new)
                .orElse(null);
    }

    public MemberDto editNickname(String email, String nickname) {
        Member member = queryRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException("멤버 검색 실패"));
        member.updateRole(MemberRole.USER);
        validationDuplicationNickname(email, nickname, member);
        member.editNickname(nickname);
        return new MemberDto(member);
    }

    private void validationDuplicationNickname(String email, String nickname, Member member) {
        if (member.getNickname() != null && !member.getNickname().equals(nickname)) {
            if (nickname.isEmpty() || nickname.length() > 16)
                throw new ClientException("유효 하지 않은 닉네임 입니다.");
            if (queryRepository.isDuplicationNickname(email, nickname))
                throw new ConflictException("중복된 닉네임 입니다.");
        }
    }

}
