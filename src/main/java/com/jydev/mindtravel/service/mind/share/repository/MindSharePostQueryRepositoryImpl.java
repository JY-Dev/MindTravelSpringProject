package com.jydev.mindtravel.service.mind.share.repository;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostResponse;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostsRequest;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.jydev.mindtravel.service.mind.share.domain.QMindSharePost.mindSharePost;
import static com.jydev.mindtravel.service.mind.share.domain.QMindSharePostChildComment.mindSharePostChildComment;
import static com.jydev.mindtravel.service.mind.share.domain.QMindSharePostComment.mindSharePostComment;

@RequiredArgsConstructor
@Repository
public class MindSharePostQueryRepositoryImpl implements MindSharePostQueryRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<MindSharePostResponse> searchMindSharePosts(MindSharePostsRequest request) {
        return queryFactory.select(Projections.fields(MindSharePostResponse.class, mindSharePost.id,
                        mindSharePost.member.nickname,
                        mindSharePost.title,
                        mindSharePost.viewCount,
                        ExpressionUtils.as(
                                JPAExpressions.select(
                                                Expressions.asNumber(
                                                        mindSharePostComment.countDistinct().add(mindSharePostChildComment.countDistinct())
                                                ))
                                        .from(mindSharePostComment)
                                        .leftJoin(mindSharePostComment.childComments, mindSharePostChildComment)
                                        .where(mindSharePostComment.postId.eq(mindSharePost.id)),
                                "commentCount"
                        ),
                        mindSharePost.createdDate))
                .from(mindSharePost)
                .where(mindSharePost.category.eq(request.getCategory()))
                .offset(request.getPageOffset() * request.getPageSize())
                .limit(request.getPageSize())
                .orderBy(mindSharePost.id.desc())
                .fetch();
    }

    @Override
    public Long searchMindSharePostsTotalSize() {
        return queryFactory.select(mindSharePost.count())
                .from(mindSharePost)
                .fetchOne();
    }

    @Override
    public Optional<MindSharePost> searchMindSharePost(Long postId) {
        MindSharePost post = queryFactory.selectFrom(mindSharePost)
                .leftJoin(mindSharePost.comments, mindSharePostComment).fetchJoin()
                .leftJoin(mindSharePostComment.childComments, mindSharePostChildComment).fetchJoin()
                .where(mindSharePost.id.eq(postId))
                .fetchOne();
        return Optional.ofNullable(post);
    }

    @Override
    public Long increaseViewCount(Long postId) {
        return queryFactory.update(mindSharePost)
                .set(mindSharePost.viewCount,mindSharePost.viewCount.add(1))
                .where(mindSharePost.id.eq(postId))
                .execute();
    }

    @Override
    public void deleteMindSharePostComment(Long commentId) {
        queryFactory.update(mindSharePostComment)
                .set(mindSharePostComment.isDeleted,Expressions.asBoolean(true))
                .execute();
    }
}
