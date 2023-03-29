package com.jydev.mindtravel.mindshare;

import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostChildComment;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jydev.mindtravel.member.MemberMockFactory.getMember;

public class MindShareMockFactory {
    public static MindSharePostDetailResponse getMindSharePostDetailResponse() {

        MindSharePostChildCommentResponse childCommentResponse = MindSharePostChildCommentResponse.builder()
                .commentId(0L)
                .nickname("nickname")
                .content("content")
                .tagNickname("tagNickname")
                .createdDate(LocalDateTime.now())
                .build();
        List<MindSharePostChildCommentResponse> childComments = new ArrayList<>();
        childComments.add(childCommentResponse);
        MindSharePostCommentResponse commentResponse = MindSharePostCommentResponse.builder()
                .childComments(childComments)
                .content("content")
                .commentId(0L)
                .nickname("nickname")
                .childComments(childComments)
                .createdDate(LocalDateTime.now()).build();
        List<MindSharePostCommentResponse> comments = new ArrayList<>();
        comments.add(commentResponse);
        return MindSharePostDetailResponse.builder()
                .comments(comments)
                .postId(0L)
                .nickname("nickname")
                .likeCount(20L)
                .viewCount(20L)
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
                .nickname("nickname")
                .likeCount(20L)
                .title("title")
                .commentCount(30L)
                .createdDate(LocalDateTime.now())
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
