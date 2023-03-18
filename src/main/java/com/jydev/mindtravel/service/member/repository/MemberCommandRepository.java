package com.jydev.mindtravel.service.member.repository;

import com.jydev.mindtravel.service.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommandRepository extends JpaRepository<Member, Long> {
}
