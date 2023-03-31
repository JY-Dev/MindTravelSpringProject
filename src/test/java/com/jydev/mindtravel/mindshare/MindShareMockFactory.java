package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.member.model.MemberResponse;
import com.jydev.mindtravel.service.member.model.MemberRole;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.model.*;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostChildCommentResponse;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentResponse;
import com.jydev.mindtravel.service.mind.share.model.like.MindSharePostLikeResponse;
import com.jydev.mindtravel.service.mind.share.model.post.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MindShareMockFactory {
    public static MindSharePostDetailResponse getMindSharePostDetailResponse() {
        List<MindSharePostChildCommentResponse> childComments = new ArrayList<>();
        childComments.add(getMindSharePostChildCommentResponse());

        List<MindSharePostCommentResponse> comments = new ArrayList<>();
        comments.add(getMindSharePostCommentResponse(childComments));

        List<MindSharePostLikeResponse> likes = new ArrayList<>();
        likes.add(getMindSharePostLikeResponse());

        return MindSharePostDetailResponse.builder()
                .comments(comments)
                .postId(0L)
                .category(MindSharePostCategory.DAILY)
                .member(getMemberResponse())
                .viewCount(20L)
                .likes(likes)
                .commentCount(2L)
                .title("title")
                .content("content")
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static MindSharePostResponse getMindSharePostResponse() {
        return MindSharePostResponse.builder()
                .postId(0L)
                .viewCount(20L)
                .category(MindSharePostCategory.DAILY)
                .member(getMemberResponse())
                .title("title")
                .commentCount(30L)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static MemberResponse getMemberResponse(){
        return MemberResponse
                .builder()
                .nickname("nickname")
                .id(0L)
                .profileImgUrl("profileImage")
                .role(MemberRole.USER)
                .build();
    }

    public static List<MindSharePostResponse> getMindSharePostResponses(long size) {
        List<MindSharePostResponse> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(getMindSharePostResponse());
        }
        return result;
    }

    public static MindSharePostRequest getMindSharePostRequest(MindSharePostCategory category) {
        return MindSharePostRequest.builder()
                .title("title")
                .content("content")
                .category(category)
                .build();
    }

    public static MindSharePostsRequest getMindSharePostsRequest(MindSharePostCategory category) {
        return MindSharePostsRequest.builder()
                .category(category)
                .pageSize(10)
                .pageOffset(0L)
                .build();
    }

    public static MindSharePostsResponse getMindSharePostsResponse(long size) {
        return MindSharePostsResponse.builder()
                .posts(getMindSharePostResponses(size)).totalPostSize(size).build();
    }

    public static MindSharePostLikeResponse getMindSharePostLikeResponse() {
        return MindSharePostLikeResponse.builder()
                .postId(0L).member(getMemberResponse()).createdDate(LocalDateTime.now()).build();
    }

    public static MindSharePostCommentResponse getMindSharePostCommentResponse(List<MindSharePostChildCommentResponse> childComments) {
        return MindSharePostCommentResponse.builder()
                .childComments(childComments)
                .content("content")
                .commentId(0L)
                .member(getMemberResponse())
                .childComments(childComments)
                .createdDate(LocalDateTime.now()).build();
    }

    public static MindSharePostChildCommentResponse getMindSharePostChildCommentResponse() {
        return MindSharePostChildCommentResponse.builder()
                .commentId(0L)
                .member(getMemberResponse())
                .content("content")
                .tagNickname("tagNickname")
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static MindSharePostCommentRequest getMindSharePostCommentRequest(long postId) {
        return MindSharePostCommentRequest.builder()
                .postId(postId)
                .content("content")
                .build();
    }

    public static List<MindSharePostComment> getMindSharePostComments(Member member, long postId, int size) {
        List<MindSharePostComment> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(new MindSharePostComment(member, getMindSharePostCommentRequest(postId)));
        }
        return result;
    }

    public static MindSharePostChildCommentRequest getMindSharePostChildCommentRequest(long parentCommentId) {
        return MindSharePostChildCommentRequest.builder()
                .parentCommentId(parentCommentId)
                .content("content")
                .build();
    }

    public static List<MindSharePostChildComment> getMindSharePostChildComments(Member member, long parentCommentId, int size) {
        List<MindSharePostChildComment> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(new MindSharePostChildComment(member, null, getMindSharePostChildCommentRequest(parentCommentId)));
        }
        return result;
    }

    public static List<MindSharePostComment> getMindSharePostCommentsWithChildComments(Member member, long postId, int size) {
        List<MindSharePostComment> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            MindSharePostComment comment = new MindSharePostComment(member, getMindSharePostCommentRequest(postId));
            List<MindSharePostChildComment> childComment = getMindSharePostChildComments(member, i, size);
            comment.addChildComment(childComment);
            result.add(comment);
        }
        return result;
    }
}
