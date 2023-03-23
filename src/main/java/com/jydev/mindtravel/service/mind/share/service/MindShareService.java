package com.jydev.mindtravel.service.mind.share.service;

import com.jydev.mindtravel.service.exception.ClientException;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.model.WriteMindSharePostRequest;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MindShareService {
    private final MemberQueryRepository memberQueryRepository;
    private final MindSharePostCommandRepository mindSharePostCommandRepository;

    public void saveMindSharePost(String email, WriteMindSharePostRequest request){
        Member member = memberQueryRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException("유저 정보가 없습니다."));
        MindSharePost mindSharePost = new MindSharePost(member,request);
        mindSharePostCommandRepository.save(mindSharePost);
    }
}
