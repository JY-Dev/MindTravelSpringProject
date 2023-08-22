package com.jydev.mindtravel.domain.member.repository;

import com.jydev.mindtravel.domain.member.domain.Member;

import java.util.Optional;

public interface MemberQueryRepository {
    Optional<Member> findByEmail(String email);

    boolean isDuplicationNickname(String email, String nickname);
}
