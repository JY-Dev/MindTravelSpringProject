package com.jydev.mindtravel.domain.member.repository;

import com.jydev.mindtravel.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommandRepository extends JpaRepository<Member, Long> {
}
