package com.jydev.mindtravel.service.mind.share.repository;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostsRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jydev.mindtravel.service.mind.share.domain.QMindSharePost.mindSharePost;

@RequiredArgsConstructor
@Repository
public class MindSharePostQueryRepositoryImpl implements MindSharePostQueryRepository{
    private final JPAQueryFactory queryFactory;


    @Override
    public List<MindSharePost> searchMindSharePosts(MindSharePostsRequest request) {
        return queryFactory.selectFrom(mindSharePost)
                .where(mindSharePost.category.eq(request.getCategory()))
                .offset(request.getPageOffset()*request.getPageSize())
                .limit(request.getPageSize())
                .fetch();
    }

    @Override
    public Long searchMindSharePostsTotalSize() {
        return queryFactory.select(mindSharePost.count())
                .from(mindSharePost)
                .fetchOne();
    }
}
