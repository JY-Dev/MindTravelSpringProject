package com.jydev.mindtravel.service.mind.share.repository;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostLike;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostResponse;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostsRequest;

import java.util.List;
import java.util.Optional;

public interface MindSharePostQueryRepository {
    List<MindSharePostResponse> searchMindSharePosts(MindSharePostsRequest request);
    Long searchMindSharePostsTotalSize(MindSharePostCategory category);
    Optional<MindSharePost> searchMindSharePost(Long postId);
    Long increaseViewCount(Long postId);
    void deleteMindSharePostComment(Long commentId);
    List<MindSharePostLike> getPostLikes(Long postId);
    List<MindSharePostComment> getPostComments(Long postId);
}
