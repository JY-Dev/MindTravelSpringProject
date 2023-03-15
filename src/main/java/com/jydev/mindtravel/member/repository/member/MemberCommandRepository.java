package com.jydev.mindtravel.member.repository.member;

import com.jydev.mindtravel.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberCommandRepository extends JpaRepository<Member, Long> {
}
