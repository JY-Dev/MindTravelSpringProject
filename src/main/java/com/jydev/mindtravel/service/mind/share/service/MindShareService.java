package com.jydev.mindtravel.service.mind.share.service;

import com.jydev.mindtravel.service.exception.ClientException;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.service.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostLike;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentResponse;
import com.jydev.mindtravel.service.mind.share.model.like.MindSharePostLikeResponse;
import com.jydev.mindtravel.service.mind.share.model.post.*;
import com.jydev.mindtravel.service.mind.share.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MindShareService {
    private final MemberCommandRepository memberCommandRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MindSharePostCommandRepository postCommandRepository;
    private final MindSharePostQueryRepository postQueryRepository;

    private final MindSharePostCommentCommandRepository commentCommandRepository;
    private final MindSharePostChildCommentCommandRepository childCommentCommandRepository;
    private final MindSharePostLikeCommandRepository likeCommandRepository;

    public void saveMindSharePost(String email, MindSharePostRequest request) {
        Member member = memberQueryRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException("유저 정보가 없습니다."));
        MindSharePost mindSharePost = new MindSharePost(member, request);
        postCommandRepository.save(mindSharePost);
    }

    public MindSharePostsResponse searchMindSharePosts(MindSharePostsRequest request) {
        List<MindSharePostResponse> mindSharePosts = postQueryRepository.searchMindSharePosts(request);
        Long totalSize = postQueryRepository.searchMindSharePostsTotalSize(request.getCategory());
        return new MindSharePostsResponse(totalSize, mindSharePosts);
    }

    public MindSharePostDetailResponse searchMindSharePost(Long postId) {
        MindSharePost post = postQueryRepository
                .searchMindSharePost(postId).orElseThrow(() -> new ClientException("글이 존재하지 않습니다."));
        postQueryRepository.increaseViewCount(postId);
        return new MindSharePostDetailResponse(post.getViewCount() + 1, post);
    }

    public List<MindSharePostLikeResponse> getPostLikes(Long postId) {
        postCommandRepository.findById(postId)
                .orElseThrow(() -> new ClientException("글이 존재하지 않습니다."));
        return postQueryRepository.getPostLikes(postId).stream()
                .map(MindSharePostLikeResponse::new).toList();
    }

    public List<MindSharePostCommentResponse> getPostComments(Long postId) {
        postCommandRepository.findById(postId)
                .orElseThrow(() -> new ClientException("글이 존재하지 않습니다."));
        return postQueryRepository.getPostComments(postId).stream()
                .map(MindSharePostCommentResponse::new).toList();
    }

    public void insertPostLike(Long postId, String email) {
        Member member = memberQueryRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException("유저 정보가 없습니다."));
        likeCommandRepository.save(new MindSharePostLike(postId, member));
    }

    public void deletePostLike(Long postId, Long memberId) {
        postQueryRepository.searchMindSharePostLike(postId, memberId)
                .ifPresent(likeCommandRepository::delete);
    }


    public void insertPostComment(MindSharePostCommentRequest request, Long memberId) {
        Member member = memberCommandRepository.findById(memberId)
                .orElseThrow(() -> new ClientException("유저 정보가 없습니다."));
        MindSharePostComment comment = new MindSharePostComment(member, request);
        commentCommandRepository.save(comment);
    }

    public void deletePostComment(Long commentId, Long memberId) {
        MindSharePostComment comment = commentCommandRepository.findById(commentId)
                .orElseThrow(() -> new ClientException("댓글 정보가 없습니다."));
        boolean isCreator = comment.isCreator(memberId);
        if (isCreator) {
            comment.delete();
        } else {
            throw new ClientException("댓글을 삭제 할 수 없습니다.");
        }
    }

    public void insertPostChildComment(MindSharePostChildCommentRequest request, Long memberId) {
        Member member = memberCommandRepository.findById(memberId)
                .orElseThrow(() -> new ClientException("유저 정보가 없습니다."));
        Member tagMember = null;
        if (request.hasTagMember()) {
            tagMember = memberCommandRepository.findById(request.getTagMemberId())
                    .orElse(null);
        }
        MindSharePostChildComment comment = new MindSharePostChildComment(member, tagMember, request);
        childCommentCommandRepository.save(comment);
    }

    public void deletePostChildComment(Long commentId, Long memberId) {
        MindSharePostChildComment comment = childCommentCommandRepository.findById(commentId)
                .orElseThrow(() -> new ClientException("댓글 정보가 없습니다."));
        boolean isCreator = comment.isCreator(memberId);
        if (isCreator) {
            childCommentCommandRepository.delete(comment);
        } else {
            throw new ClientException("댓글을 삭제 할 수 없습니다.");
        }
    }
}
