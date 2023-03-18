package com.jydev.mindtravel.service.member.repository;

import com.jydev.mindtravel.service.member.domain.Member;

import java.util.Optional;

public interface MemberQueryRepository {
    Optional<Member> findByEmail(String email);
    boolean isDuplicationNickname(String email,String nickname);
}
