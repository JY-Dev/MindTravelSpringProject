package com.jydev.mindtravel.domain.mind.share.repository;

import com.jydev.mindtravel.domain.member.dto.MemberResponse;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostCategory;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostLike;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostResponse;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostsRequest;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.jydev.mindtravel.domain.mind.share.domain.QMindSharePost.mindSharePost;
import static com.jydev.mindtravel.domain.mind.share.domain.QMindSharePostChildComment.mindSharePostChildComment;
import static com.jydev.mindtravel.domain.mind.share.domain.QMindSharePostComment.mindSharePostComment;
import static com.jydev.mindtravel.domain.mind.share.domain.QMindSharePostLike.mindSharePostLike;


@RequiredArgsConstructor
@Repository
public class MindSharePostQueryRepositoryImpl implements MindSharePostQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MindSharePostResponse> searchMindSharePosts(MindSharePostsRequest request) {
        QBean<MemberResponse> memberResponseQBean = Projections.fields(MemberResponse.class,
                mindSharePost.member.id,
                mindSharePost.member.nickname,
                mindSharePost.member.profileImgUrl,
                mindSharePost.member.role
        );
        JPQLQuery<Long> commentCountExpressions = JPAExpressions.select(
                        Expressions.asNumber(
                                mindSharePostComment.countDistinct().add(mindSharePostChildComment.countDistinct())
                        ))
                .from(mindSharePostComment)
                .leftJoin(mindSharePostComment.childComments, mindSharePostChildComment)
                .where(mindSharePostComment.postId.eq(mindSharePost.id));
        return queryFactory.select(Projections.fields(MindSharePostResponse.class,
                        ExpressionUtils.as(mindSharePost.id, "postId"),
                        ExpressionUtils.as(memberResponseQBean, "member"),
                        mindSharePost.title,
                        mindSharePost.category,
                        mindSharePost.viewCount,
                        ExpressionUtils.as(commentCountExpressions, "commentCount"),
                        mindSharePost.createdDate))
                .from(mindSharePost)
                .where(mindSharePost.category.eq(request.getCategory()))
                .offset(request.getPageOffset() * request.getPageSize())
                .limit(request.getPageSize())
                .orderBy(mindSharePost.id.desc())
                .fetch();
    }

    @Override
    public Long searchMindSharePostsTotalSize(MindSharePostCategory category) {
        return queryFactory.select(mindSharePost.count())
                .where(mindSharePost.category.eq(category))
                .from(mindSharePost)
                .fetchOne();
    }

    @Override
    public Optional<MindSharePost> searchMindSharePost(Long postId) {
        MindSharePost post = queryFactory.selectFrom(mindSharePost)
                .leftJoin(mindSharePost.comments, mindSharePostComment).fetchJoin()
                .leftJoin(mindSharePostComment.childComments, mindSharePostChildComment).fetchJoin()
                .leftJoin(mindSharePost.likes, mindSharePostLike).fetchJoin()
                .where(mindSharePost.id.eq(postId))
                .fetchOne();
        return Optional.ofNullable(post);
    }

    @Override
    public Optional<MindSharePostLike> searchMindSharePostLike(Long postId, Long memberId) {
        return Optional.ofNullable(queryFactory.selectFrom(mindSharePostLike)
                .where(mindSharePostLike.member.id.eq(memberId).and(mindSharePostLike.postId.eq(postId)))
                .fetchOne());
    }

    @Override
    public void increaseViewCount(Long postId) {
        queryFactory.update(mindSharePost)
                .set(mindSharePost.viewCount, mindSharePost.viewCount.add(1))
                .where(mindSharePost.id.eq(postId))
                .execute();
    }

    @Override
    public List<MindSharePostLike> getPostLikes(Long postId) {
        return queryFactory.selectFrom(mindSharePostLike)
                .where(mindSharePostLike.postId.eq(postId))
                .fetch();
    }

    @Override
    public List<MindSharePostComment> getPostComments(Long postId) {
        return queryFactory.selectFrom(mindSharePostComment)
                .where(mindSharePostComment.postId.eq(postId))
                .leftJoin(mindSharePostComment.childComments, mindSharePostChildComment).fetchJoin()
                .fetch();
    }
}
