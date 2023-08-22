package com.jydev.mindtravel.domain.mind.share.repository;

import com.jydev.mindtravel.domain.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostCategory;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostLike;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostResponse;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostsRequest;

import java.util.List;
import java.util.Optional;

public interface MindSharePostQueryRepository {
    List<MindSharePostResponse> searchMindSharePosts(MindSharePostsRequest request);

    Long searchMindSharePostsTotalSize(MindSharePostCategory category);

    Optional<MindSharePost> searchMindSharePost(Long postId);

    Optional<MindSharePostLike> searchMindSharePostLike(Long postId, Long memberId);

    void increaseViewCount(Long postId);

    List<MindSharePostLike> getPostLikes(Long postId);

    List<MindSharePostComment> getPostComments(Long postId);
}
