package com.jydev.mindtravel.domain.member.service;

import com.jydev.mindtravel.common.exception.NotFoundException;
import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.domain.member.repository.MemberQueryRepository;

public class MemberUtils {
    public static Member findMemberByEmail(MemberQueryRepository repository, String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(NotFoundException.MEMBER));
    }

    public static Member findMemberById(MemberCommandRepository repository, Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundException.MEMBER));
    }
}
