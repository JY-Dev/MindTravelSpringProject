package com.jydev.mindtravel.service.mind.share.repository;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostResponse;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostsRequest;

import java.util.List;
import java.util.Optional;

public interface MindSharePostQueryRepository {
    List<MindSharePostResponse> searchMindSharePosts(MindSharePostsRequest request);
    Long searchMindSharePostsTotalSize();
    Optional<MindSharePost> searchMindSharePost(Long postId);
    Long increaseViewCount(Long postId);
    void deleteMindSharePostComment(Long commentId);
}
