package com.jydev.mindtravel.member.repository.member;

import com.jydev.mindtravel.member.domain.Member;

import java.util.Optional;

public interface MemberQueryRepository {
    Optional<Member> findByEmail(String email);
    boolean isDuplicationNickname(String email,String nickname);
}
