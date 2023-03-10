package com.jydev.mindtravel.member.service;

import com.jydev.mindtravel.member.domain.Member;
import com.jydev.mindtravel.member.model.MemberDto;
import com.jydev.mindtravel.member.repository.MemberRepository;
import com.jydev.mindtravel.web.security.oauth.model.OauthInfo;
import com.jydev.mindtravel.web.security.oauth.model.OauthServerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberDto socialLogin(OauthServerType type, OauthInfo info){
        Member member = repository.findByEmail(info.getEmail())
                .orElseGet(() -> repository.save(new Member(type, info)));
        return new MemberDto(member);
    }

    public MemberDto findMemberByEmail(String email){
        return repository.findByEmail(email)
                .map(MemberDto::new)
                .orElse(null);
    }
}
