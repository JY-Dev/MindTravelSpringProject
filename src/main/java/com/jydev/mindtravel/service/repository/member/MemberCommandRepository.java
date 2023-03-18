package com.jydev.mindtravel.service.repository.member;

import com.jydev.mindtravel.service.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommandRepository extends JpaRepository<Member, Long> {
}
