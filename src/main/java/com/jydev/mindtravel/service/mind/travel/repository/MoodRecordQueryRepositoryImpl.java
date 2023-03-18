package com.jydev.mindtravel.service.mind.travel.repository;

import com.jydev.mindtravel.service.mind.travel.domain.MoodRecord;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.jydev.mindtravel.service.mind.travel.domain.QMoodRecord.moodRecord;

@RequiredArgsConstructor
@Repository
public class MoodRecordQueryRepositoryImpl implements MoodRecordQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MoodRecord> searchMoodRecords(String email, String date) {
        StringExpression formattedDate = Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", moodRecord.createdDate);
        return queryFactory.selectFrom(moodRecord)
                .where(moodRecord.member.email.eq(email).and(formattedDate.eq(date)))
                .orderBy(moodRecord.createdDate.desc())
                .fetch();
    }

    @Override
    public Optional<MoodRecord> findMoodRecord(String email, Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(moodRecord)
                .where(moodRecord.member.email.eq(email).and(moodRecord.id.eq(id)))
                .fetchOne());
    }
}
