package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.domain.member.domain.Member;
import com.jydev.mindtravel.domain.member.dto.MemberResponse;
import com.jydev.mindtravel.domain.member.domain.MemberRole;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostCategory;
import com.jydev.mindtravel.domain.mind.share.dto.post.*;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostChildCommentResponse;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentResponse;
import com.jydev.mindtravel.domain.mind.share.dto.like.MindSharePostLikeResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MindShareMockFactory {
    public static MindSharePostDetailResponse getMindSharePostDetailResponse() {
        List<MindSharePostCommentResponse> comments = getMindSharePostCommentResponses();

        List<MindSharePostLikeResponse> likes = getMindSharePostLikeResponses();

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
                .isDeleted(false)
                .member(getMemberResponse())
                .childComments(childComments)
                .createdDate(LocalDateTime.now()).build();
    }

    public static MindSharePostChildCommentResponse getMindSharePostChildCommentResponse() {
        return MindSharePostChildCommentResponse.builder()
                .commentId(0L)
                .parentCommentId(1L)
                .member(getMemberResponse())
                .content("content")
                .tagNickname("tagNickname")
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static MindSharePostCommentRequest getMindSharePostCommentRequest(long postId) {
        return MindSharePostCommentRequest.builder()
                .memberId(0L)
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
                .postId(0L)
                .tagMemberId(0L)
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

    public static List<MindSharePostCommentResponse> getMindSharePostCommentResponses(){
        List<MindSharePostChildCommentResponse> childComments = new ArrayList<>();
        childComments.add(getMindSharePostChildCommentResponse());

        List<MindSharePostCommentResponse> comments = new ArrayList<>();
        comments.add(getMindSharePostCommentResponse(childComments));
        return comments;
    }

    public static List<MindSharePostLikeResponse> getMindSharePostLikeResponses(){
        List<MindSharePostLikeResponse> likes = new ArrayList<>();
        likes.add(getMindSharePostLikeResponse());
        return likes;
    }
}
