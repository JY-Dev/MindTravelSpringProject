package com.jydev.mindtravel.mindshare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.ControllerTest;
import com.jydev.mindtravel.service.mind.share.model.*;
import com.jydev.mindtravel.service.mind.share.service.MindShareService;
import com.jydev.mindtravel.util.ControllerTestHelper;
import com.jydev.mindtravel.web.controller.MindShareController;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.jydev.mindtravel.ApiDocumentUtils.getDocumentRequest;
import static com.jydev.mindtravel.ApiDocumentUtils.getDocumentResponse;
import static com.jydev.mindtravel.mindshare.MindShareMockFactory.getMindSharePostDetailResponse;
import static com.jydev.mindtravel.mindshare.MindShareMockFactory.getMindSharePostsResponse;
import static com.jydev.mindtravel.util.ControllerTestHelper.baseRequestBuilder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
@WebMvcTest(MindShareController.class)
public class MindShareControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HttpUtils httpUtils;
    @MockBean
    private MindShareService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveMindSharePostTest() throws Exception {
        MindSharePostRequest request = MindShareMockFactory.getMindSharePostRequest(MindSharePostCategory.DAILY);
        given(httpUtils.makeEmptyResponse()).willReturn(
                ResponseEntity.ok(new HttpResponse<EmptyResponse>(HttpServletResponse.SC_OK, "", null))
        );
        ResultActions requestAction = mockMvc.perform(
                ControllerTestHelper.baseRequestBuilder(
                        post("/v1/mind/share/post")
                                .content(objectMapper.writeValueAsString(request))
                )
        );
        requestAction.andExpect(status().isOk())
                .andDo(document("save-mind-share-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("본문"),
                                fieldWithPath("category").description("카테고리 종류 : TROUBLE_COUNSELING, DAILY")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )));
    }

    @Test
    public void searchMindSharePostsTest() throws Exception {
        MindSharePostsResponse mindSharePostsResponse = getMindSharePostsResponse(1);
        HttpResponse<MindSharePostsResponse> response = new HttpResponse<>(HttpServletResponse.SC_OK, "", mindSharePostsResponse);
        given(service.searchMindSharePosts(any(MindSharePostsRequest.class))).willReturn(mindSharePostsResponse);
        given(httpUtils.makeHttpResponse(any(Integer.class), any(String.class), any(MindSharePostsResponse.class))).willReturn(
                ResponseEntity.ok(response)
        );
        ResultActions resultActions = this.mockMvc.perform(
                baseRequestBuilder(
                        get("/v1/mind/share/post")
                                .queryParam("pageOffset", "0")
                                .queryParam("pageSize", "10")
                                .queryParam("category", "TROUBLE_COUNSELING")
                )
        );
        resultActions.andExpect(status().isOk())
                .andDo(document("search-mind-share-posts",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")),
                        queryParameters(
                                parameterWithName("pageOffset").description("Offset"),
                                parameterWithName("pageSize").description("pageSize"),
                                parameterWithName("category").description("카테고리 종류 : TROUBLE_COUNSELING, DAILY")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.totalPostSize").type(JsonFieldType.NUMBER).description("전체 글 사이즈"),
                                fieldWithPath("data.posts[].postId").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.posts[].nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("data.posts[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.posts[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 수"),
                                fieldWithPath("data.posts[].viewCount").type(JsonFieldType.NUMBER).description("방문 수"),
                                fieldWithPath("data.posts[].commentCount").type(JsonFieldType.NUMBER).description("댓글 수"),
                                fieldWithPath("data.posts[].createdDate").type(JsonFieldType.STRING).description("생성 날짜")
                        )));
    }

    @Test
    public void searchMindSharePostTest() throws Exception {
        MindSharePostDetailResponse postDetail = getMindSharePostDetailResponse();
        HttpResponse<MindSharePostDetailResponse> response = new HttpResponse<>(HttpServletResponse.SC_OK, "", postDetail);
        given(service.searchMindSharePost(any(Long.class))).willReturn(postDetail);
        given(httpUtils.makeHttpResponse(any(Integer.class), any(String.class), any(MindSharePostDetailResponse.class))).willReturn(
                ResponseEntity.ok(response)
        );
        ResultActions resultActions = this.mockMvc.perform(
                baseRequestBuilder(
                        get("/v1/mind/share/post/{postId}",1)
                )
        );
        resultActions.andExpect(status().isOk())
                .andDo(document("search-mind-share-post-detail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")),
                        pathParameters(
                                parameterWithName("postId").description("글 Id")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("글 Id"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("글 작성자 닉네임"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("글 내용"),
                                fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 수"),
                                fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("댓글 수"),
                                fieldWithPath("data.createdDate").type(JsonFieldType.STRING).description("글 생성일"),
                                fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER).description("댓글 Id"),
                                fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                                fieldWithPath("data.comments[].nickname").type(JsonFieldType.STRING).description("댓글 작성자 닉네임"),
                                fieldWithPath("data.comments[].createdDate").type(JsonFieldType.STRING).description("댓글 생성일"),
                                fieldWithPath("data.comments[].childComments[].commentId").type(JsonFieldType.NUMBER).description("대댓글 Id"),
                                fieldWithPath("data.comments[].childComments[].content").type(JsonFieldType.STRING).description("대댓글 내용"),
                                fieldWithPath("data.comments[].childComments[].nickname").type(JsonFieldType.STRING).description("대댓글 작성자 닉네임"),
                                fieldWithPath("data.comments[].childComments[].tagNickname").type(JsonFieldType.STRING).description("대댓글 태그 대상 닉네임"),
                                fieldWithPath("data.comments[].childComments[].createdDate").type(JsonFieldType.STRING).description("대댓글 생성일")
                        )));
    }
}
