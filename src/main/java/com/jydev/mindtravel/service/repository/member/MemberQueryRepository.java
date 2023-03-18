package com.jydev.mindtravel.service.repository.member;

import com.jydev.mindtravel.service.domain.Member;

import java.util.Optional;

public interface MemberQueryRepository {
    Optional<Member> findByEmail(String email);
    boolean isDuplicationNickname(String email,String nickname);
}
