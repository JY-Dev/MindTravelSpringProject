package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.domain.member.dto.MemberDto;
import com.jydev.mindtravel.domain.mind.share.domain.MindSharePostCategory;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostChildCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentEditRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentRequest;
import com.jydev.mindtravel.domain.mind.share.dto.comment.MindSharePostCommentResponse;
import com.jydev.mindtravel.domain.mind.share.dto.like.MindSharePostLikeResponse;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostDetailResponse;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostRequest;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostsRequest;
import com.jydev.mindtravel.domain.mind.share.dto.post.MindSharePostsResponse;
import com.jydev.mindtravel.domain.mind.share.service.MindShareService;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/mind/share")
@Controller
public class MindShareController {
    private final HttpUtils httpUtils;
    private final MindShareService mindShareService;

    @PostMapping("/post")
    public HttpResponse<EmptyResponse> saveMindSharePost(@RequestAttribute("member") MemberDto member,
                                                         @RequestBody MindSharePostRequest request) {
        mindShareService.saveMindSharePost(member.getEmail(), request);
        return httpUtils.makeEmptyResponse();
    }

    @GetMapping("/post")
    public HttpResponse<MindSharePostsResponse> searchMindSharePosts(Long pageOffset, Integer pageSize, MindSharePostCategory category) {
        MindSharePostsRequest request = MindSharePostsRequest.builder()
                .pageOffset(pageOffset)
                .pageSize(pageSize)
                .category(category)
                .build();
        MindSharePostsResponse data = mindShareService.searchMindSharePosts(request);
        return httpUtils.makeHttpResponse("", data);
    }

    @GetMapping("/post/{postId}")
    public HttpResponse<MindSharePostDetailResponse> searchMindSharePost(@PathVariable Long postId) {
        MindSharePostDetailResponse data = mindShareService.searchMindSharePost(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @GetMapping("/post/{postId}/comments")
    public HttpResponse<List<MindSharePostCommentResponse>> getMindSharePostComments(@PathVariable Long postId) {
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @GetMapping("/post/{postId}/likes")
    public HttpResponse<List<MindSharePostLikeResponse>> getMindSharePostLikes(@PathVariable Long postId) {
        List<MindSharePostLikeResponse> data = mindShareService.getPostLikes(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @PostMapping("/post/{postId}/like")
    public HttpResponse<List<MindSharePostLikeResponse>> insertMindSharePostLike(@PathVariable Long postId,
                                                                                 @RequestAttribute("member") MemberDto member) {
        mindShareService.insertPostLike(postId, member.getEmail());
        List<MindSharePostLikeResponse> data = mindShareService.getPostLikes(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @DeleteMapping("/post/{postId}/like")
    public HttpResponse<List<MindSharePostLikeResponse>> deleteMindSharePostLike(@PathVariable Long postId,
                                                                                 @RequestAttribute("member") MemberDto member) {
        mindShareService.deletePostLike(postId, member.getMemberIdx());
        List<MindSharePostLikeResponse> data = mindShareService.getPostLikes(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @PostMapping("/post/{postId}/comment")
    public HttpResponse<List<MindSharePostCommentResponse>> insertMindSharePostComment(@PathVariable Long postId,
                                                                                       String content,
                                                                                       @RequestAttribute("member") MemberDto member) {
        MindSharePostCommentRequest request = MindSharePostCommentRequest.builder()
                .postId(postId)
                .content(content)
                .memberId(member.getMemberIdx())
                .build();
        mindShareService.insertPostComment(request);
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public HttpResponse<List<MindSharePostCommentResponse>> deleteMindSharePostComment(@PathVariable Long postId,
                                                                                       @PathVariable Long commentId,
                                                                                       @RequestAttribute("member") MemberDto member) {
        mindShareService.deletePostComment(commentId, member.getMemberIdx());
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @PatchMapping("/post/{postId}/comment/{commentId}")
    public HttpResponse<List<MindSharePostCommentResponse>> editMindSharePostComment(@PathVariable Long postId,
                                                                                     @PathVariable Long commentId,
                                                                                     String content,
                                                                                     @RequestAttribute("member") MemberDto member) {
        MindSharePostCommentEditRequest request = MindSharePostCommentEditRequest.builder()
                .commentId(commentId)
                .content(content)
                .memberId(member.getMemberIdx())
                .build();
        mindShareService.editPostComment(request);
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @PostMapping("/post/{postId}/comment/child")
    public HttpResponse<List<MindSharePostCommentResponse>> insertMindSharePostChildComment(@PathVariable Long postId,
                                                                                            @RequestBody MindSharePostChildCommentRequest request,
                                                                                            @RequestAttribute("member") MemberDto member) {
        request.setPostId(postId);
        mindShareService.insertPostChildComment(request, member.getMemberIdx());
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @DeleteMapping("/post/{postId}/comment/child/{commentId}")
    public HttpResponse<List<MindSharePostCommentResponse>> deleteMindSharePostChildComment(@PathVariable Long postId,
                                                                                            @PathVariable Long commentId,
                                                                                            @RequestAttribute("member") MemberDto member) {
        mindShareService.deletePostChildComment(commentId, member.getMemberIdx());
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse("", data);
    }

    @PatchMapping("/post/{postId}/comment/child/{commentId}")
    public HttpResponse<List<MindSharePostCommentResponse>> editMindSharePostChildComment(@PathVariable Long postId,
                                                                                          @PathVariable Long commentId,
                                                                                          String content,
                                                                                          @RequestAttribute("member") MemberDto member) {
        MindSharePostCommentEditRequest request = MindSharePostCommentEditRequest.builder()
                .commentId(commentId)
                .content(content)
                .memberId(member.getMemberIdx())
                .build();
        mindShareService.editPostChildComment(request);
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse("", data);
    }
}
