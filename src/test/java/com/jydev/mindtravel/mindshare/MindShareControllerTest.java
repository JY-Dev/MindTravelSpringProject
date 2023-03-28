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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jydev.mindtravel.ApiDocumentUtils.getDocumentRequest;
import static com.jydev.mindtravel.ApiDocumentUtils.getDocumentResponse;
import static com.jydev.mindtravel.util.ControllerTestHelper.baseRequestBuilder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
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
        MindSharePostRequest request = MindSharePostRequest.builder()
                .title("title")
                .content("content")
                .category(MindSharePostCategory.TROUBLE_COUNSELING)
                .build();
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
        MindSharePostResponse mindSharePostResponse = MindSharePostResponse.builder()
                .postId(0L)
                .viewCount(20L)
                .nickname("nickname")
                .likeCount(20L)
                .title("title")
                .createdDate(LocalDateTime.now())
                .build();
        List<MindSharePostResponse> responses = new ArrayList<>();
        responses.add(mindSharePostResponse);
        MindSharePostsResponse mindSharePostsResponse = MindSharePostsResponse.builder()
                        .posts(responses).totalPostSize(1L).build();
        HttpResponse<MindSharePostsResponse> response = new HttpResponse<>(HttpServletResponse.SC_OK, "", mindSharePostsResponse);
        given(service.searchMindSharePosts(any(MindSharePostsRequest.class))).willReturn(mindSharePostsResponse);
        given(httpUtils.makeHttpResponse(any(Integer.class),any(String.class),any(MindSharePostsResponse.class))).willReturn(
                ResponseEntity.ok(response)
        );
        ResultActions resultActions = this.mockMvc.perform(
                baseRequestBuilder(
                        get("/v1/mind/share/post")
                                .queryParam("pageOffset","0")
                                .queryParam("pageSize","10")
                                .queryParam("category","TROUBLE_COUNSELING")
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
                                fieldWithPath("data.posts[].createdDate").type(JsonFieldType.STRING).description("생성 날짜")
                        )));
    }
}
