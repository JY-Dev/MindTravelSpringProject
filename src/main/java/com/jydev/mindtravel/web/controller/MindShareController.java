package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentRequest;
import com.jydev.mindtravel.service.mind.share.model.comment.MindSharePostCommentResponse;
import com.jydev.mindtravel.service.mind.share.model.like.MindSharePostLikeResponse;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostDetailResponse;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostRequest;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostsRequest;
import com.jydev.mindtravel.service.mind.share.model.post.MindSharePostsResponse;
import com.jydev.mindtravel.service.mind.share.service.MindShareService;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HttpResponse<EmptyResponse>> saveMindSharePost(@RequestAttribute("member") MemberDto member,
                                                                         @RequestBody MindSharePostRequest request) {
        mindShareService.saveMindSharePost(member.getEmail(), request);
        return httpUtils.makeEmptyResponse();
    }

    @GetMapping("/post")
    public ResponseEntity<HttpResponse<MindSharePostsResponse>> searchMindSharePosts(Long pageOffset, Integer pageSize, MindSharePostCategory category) {
        MindSharePostsRequest request = MindSharePostsRequest.builder()
                .pageOffset(pageOffset)
                .pageSize(pageSize)
                .category(category)
                .build();
        MindSharePostsResponse data = mindShareService.searchMindSharePosts(request);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK, "", data);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<HttpResponse<MindSharePostDetailResponse>> searchMindSharePost(@PathVariable Long postId) {
        MindSharePostDetailResponse data = mindShareService.searchMindSharePost(postId);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK, "", data);
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<HttpResponse<List<MindSharePostCommentResponse>>> getMindSharePostComments(@PathVariable Long postId) {
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK, "", data);
    }

    @GetMapping("/post/{postId}/likes")
    public ResponseEntity<HttpResponse<List<MindSharePostLikeResponse>>> getMindSharePostLikes(@PathVariable Long postId) {
        List<MindSharePostLikeResponse> data = mindShareService.getPostLikes(postId);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK, "", data);
    }

    @PostMapping("/post/{postId}/like")
    public ResponseEntity<HttpResponse<List<MindSharePostLikeResponse>>> insertMindSharePostLike(@PathVariable Long postId,
                                                                                                 @RequestAttribute("member") MemberDto member) {
        mindShareService.insertPostLike(postId, member.getEmail());
        List<MindSharePostLikeResponse> data = mindShareService.getPostLikes(postId);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK, "", data);
    }

    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<HttpResponse<List<MindSharePostLikeResponse>>> deleteMindSharePostLike(@PathVariable Long postId,
                                                                                                 @RequestAttribute("member") MemberDto member) {
        mindShareService.deletePostLike(postId, member.getMemberIdx());
        List<MindSharePostLikeResponse> data = mindShareService.getPostLikes(postId);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK, "", data);
    }

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<HttpResponse<List<MindSharePostCommentResponse>>> insertMindSharePostComment(@PathVariable Long postId,
                                                                                                       String content,
                                                                                                       @RequestAttribute("member") MemberDto member) {
        MindSharePostCommentRequest request = MindSharePostCommentRequest.builder()
                .postId(postId)
                .content(content)
                .memberId(member.getMemberIdx())
                .build();
        mindShareService.insertPostComment(request);
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK, "", data);
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<HttpResponse<List<MindSharePostCommentResponse>>> deleteMindSharePostComment(@PathVariable Long postId,
                                                                                                       @PathVariable Long commentId,
                                                                                                       @RequestAttribute("member") MemberDto member) {
        mindShareService.deletePostComment(commentId, member.getMemberIdx());
        List<MindSharePostCommentResponse> data = mindShareService.getPostComments(postId);
        return httpUtils.makeHttpResponse(HttpServletResponse.SC_OK, "", data);
    }
}
