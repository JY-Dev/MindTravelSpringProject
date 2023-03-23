package com.jydev.mindtravel.service.mind.share.repository;

import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostListRequest;

import java.util.List;

public interface MindSharePostQueryRepository {
    List<MindSharePost> searchMindSharePosts(MindSharePostListRequest request);
}
