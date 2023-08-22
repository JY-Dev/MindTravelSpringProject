package com.jydev.mindtravel.domain.mind.share.service;

import com.jydev.mindtravel.common.exception.InvalidException;
import com.jydev.mindtravel.common.exception.NotFoundException;
import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.repository.MemberCommandRepository;
import com.jydev.mindtravel.domain.member.repository.MemberQueryRepository;
import com.jydev.mindtravel.domain.member.service.MemberUtils;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostLike;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentEditRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentResponse;
import com.jydev.mindtravel.domain.mind.share.dto.like.MindSharePostLikeResponse;
import com.jydev.mindtravel.domain.mind.share.dto.post.*;
import com.jydev.mindtravel.domain.mind.share.event.MindSharePostCommentFcmEvent;
import com.jydev.mindtravel.domain.mind.share.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ApplicationEventPublisher eventPublisher;

    public void saveMindSharePost(String email, MindSharePostRequest request) {
        Member member = MemberUtils.findMemberByEmail(memberQueryRepository, email);
        MindSharePost mindSharePost = new MindSharePost(member, request);
        postCommandRepository.save(mindSharePost);
    }


    public MindSharePostDetailResponse searchMindSharePost(Long postId) {
        MindSharePost post = postQueryRepository
                .searchMindSharePost(postId).orElseThrow(() -> new NotFoundException(NotFoundException.POST));
        postQueryRepository.increaseViewCount(postId);
        return new MindSharePostDetailResponse(post.getViewCount() + 1, post);
    }

    public void insertPostLike(Long postId, String email) {
        Member member = MemberUtils.findMemberByEmail(memberQueryRepository, email);
        likeCommandRepository.save(new MindSharePostLike(postId, member));
    }

    public void deletePostLike(Long postId, Long memberId) {
        postQueryRepository.searchMindSharePostLike(postId, memberId)
                .ifPresent(likeCommandRepository::delete);
    }


    public void insertPostComment(MindSharePostCommentRequest request) {
        Member member = MemberUtils.findMemberById(memberCommandRepository, request.getMemberId());
        MindSharePostComment comment = new MindSharePostComment(member, request);
        commentCommandRepository.save(comment);
        eventPublisher.publishEvent(new MindSharePostCommentFcmEvent(request, member));
    }

    public void deletePostComment(Long commentId, Long memberId) {
        MindSharePostComment comment = commentCommandRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(NotFoundException.COMMENT));
        boolean isCreator = comment.isCreator(memberId);
        if (isCreator) {
            comment.delete();
        } else {
            throw new InvalidException(InvalidException.DELETE_COMMENT);
        }
    }

    public void editPostComment(MindSharePostCommentEditRequest request) {
        MindSharePostComment comment = commentCommandRepository.findById(request.getCommentId())
                .orElseThrow(() -> new NotFoundException(NotFoundException.COMMENT));
        boolean isCreator = comment.isCreator(request.getMemberId());
        if (isCreator) {
            comment.editComment(request);
        } else {
            throw new InvalidException(InvalidException.UPDATE_COMMENT);
        }
    }

    public void insertPostChildComment(MindSharePostChildCommentRequest request, Long memberId) {
        Member member = MemberUtils.findMemberById(memberCommandRepository, memberId);
        MindSharePostComment parentComment = commentCommandRepository.findById(request.getParentCommentId())
                .orElseThrow(() -> new NotFoundException(NotFoundException.COMMENT));
        if (!parentComment.getPostId().equals(request.getPostId()))
            throw new InvalidException(InvalidException.DEFAULT);
        Member tagMember = null;
        if (request.hasTagMember()) {
            tagMember = memberCommandRepository.findById(request.getTagMemberId())
                    .orElse(null);
        }

        MindSharePostChildComment comment = new MindSharePostChildComment(member, tagMember, request);
        childCommentCommandRepository.save(comment);
        eventPublisher.publishEvent(new MindSharePostCommentFcmEvent(request, member, tagMember));
    }

    public void deletePostChildComment(Long commentId, Long memberId) {
        MindSharePostChildComment comment = childCommentCommandRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(NotFoundException.COMMENT));
        boolean isCreator = comment.isCreator(memberId);
        if (isCreator) {
            childCommentCommandRepository.delete(comment);
        } else {
            throw new InvalidException(InvalidException.DELETE_COMMENT);
        }
    }

    public void editPostChildComment(MindSharePostCommentEditRequest request) {
        MindSharePostChildComment comment = childCommentCommandRepository.findById(request.getCommentId())
                .orElseThrow(() -> new NotFoundException(NotFoundException.COMMENT));
        boolean isCreator = comment.isCreator(request.getMemberId());
        if (isCreator) {
            comment.editComment(request);
        } else {
            throw new InvalidException(InvalidException.UPDATE_COMMENT);
        }
    }

    private void existPost(Long postId) {
        postQueryRepository
                .searchMindSharePost(postId).orElseThrow(() -> new NotFoundException(NotFoundException.POST));
    }

    @Transactional(readOnly = true)
    public MindSharePostsResponse searchMindSharePosts(MindSharePostsRequest request) {
        List<MindSharePostResponse> mindSharePosts = postQueryRepository.searchMindSharePosts(request);
        Long totalSize = postQueryRepository.searchMindSharePostsTotalSize(request.getCategory());
        return new MindSharePostsResponse(totalSize, mindSharePosts);
    }

    @Transactional(readOnly = true)
    public List<MindSharePostLikeResponse> getPostLikes(Long postId) {
        existPost(postId);
        return postQueryRepository.getPostLikes(postId).stream()
                .map(MindSharePostLikeResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public List<MindSharePostCommentResponse> getPostComments(Long postId) {
        existPost(postId);
        return postQueryRepository.getPostComments(postId).stream()
                .map(MindSharePostCommentResponse::new).toList();
    }
}
