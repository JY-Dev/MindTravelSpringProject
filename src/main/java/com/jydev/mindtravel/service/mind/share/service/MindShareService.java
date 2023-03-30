package com.jydev.mindtravel.service.mind.share.service;

import com.jydev.mindtravel.service.exception.ClientException;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.post.*;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommandRepository;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MindShareService {
    private final MemberQueryRepository memberQueryRepository;
    private final MindSharePostCommandRepository mindSharePostCommandRepository;
    private final MindSharePostQueryRepository mindSharePostQueryRepository;

    public void saveMindSharePost(String email, MindSharePostRequest request) {
        Member member = memberQueryRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException("유저 정보가 없습니다."));
        MindSharePost mindSharePost = new MindSharePost(member, request);
        mindSharePostCommandRepository.save(mindSharePost);
    }

    public MindSharePostsResponse searchMindSharePosts(MindSharePostsRequest request) {
        List<MindSharePostResponse> mindSharePosts = mindSharePostQueryRepository.searchMindSharePosts(request);
        Long totalSize = mindSharePostQueryRepository.searchMindSharePostsTotalSize();
        return new MindSharePostsResponse(totalSize, mindSharePosts);
    }

    public MindSharePostDetailResponse searchMindSharePost(long postId){
        MindSharePost post = mindSharePostQueryRepository
                .searchMindSharePost(postId).orElseThrow(() -> new ClientException("글 정보가 없습니다."));
        Long viewCount = mindSharePostQueryRepository.increaseViewCount(postId);
        return new MindSharePostDetailResponse(viewCount,post);
    }

}
