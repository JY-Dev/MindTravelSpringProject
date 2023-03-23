package com.jydev.mindtravel.mindshare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.ControllerTest;
import com.jydev.mindtravel.service.mind.share.model.MindSharePostCategory;
import com.jydev.mindtravel.service.mind.share.model.WriteMindSharePostRequest;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
        WriteMindSharePostRequest request = WriteMindSharePostRequest.builder()
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
                                fieldWithPath("category").description("카테고리, 카테고리 종류 : TROUBLE_COUNSELING, DAILY")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )));
    }
}
