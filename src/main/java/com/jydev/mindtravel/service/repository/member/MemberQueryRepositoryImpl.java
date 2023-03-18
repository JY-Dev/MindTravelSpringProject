package com.jydev.mindtravel.service.repository.member;

import com.jydev.mindtravel.service.domain.Member;
import com.jydev.mindtravel.service.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Member> findByEmail(String email) {
        Member member = jpaQueryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(member);
    }

    @Override
    public boolean isDuplicationNickname(String email, String nickname) {
        Member member = jpaQueryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.email.ne(email)
                        .and(QMember.member.nickname.eq(nickname)))
                .fetchOne();
        return member != null;
    }
}
