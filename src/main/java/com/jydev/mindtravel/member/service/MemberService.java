package com.jydev.mindtravel.member.service;

import com.jydev.mindtravel.exception.ClientException;
import com.jydev.mindtravel.exception.ConflictException;
import com.jydev.mindtravel.member.domain.Member;
import com.jydev.mindtravel.member.model.MemberDto;
import com.jydev.mindtravel.member.model.MemberRole;
import com.jydev.mindtravel.member.repository.member.MemberCommandRepository;
import com.jydev.mindtravel.member.repository.member.MemberQueryRepository;
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

    public MemberDto socialLogin(OauthServerType type, OauthInfo info) {
        Member member = queryRepository.findByEmail(info.getEmail())
                .orElseGet(() -> commandRepository.save(new Member(type, info)));
        return new MemberDto(member);
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
        return new MemberDto(member);
    }

    private void validationDuplicationNickname(String email, String nickname, Member member) {
        if (!member.getNickname().equals(nickname)) {
            if (nickname.isEmpty() || nickname.length() > 16)
                throw new ClientException("유효 하지 않은 닉네임 입니다.");
            if (queryRepository.isDuplicationNickname(email, nickname))
                throw new ConflictException("중복된 닉네임 입니다.");
            member.editNickname(nickname);
        }
    }

}
