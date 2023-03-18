package com.jydev.mindtravel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.ControllerTest;
import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.member.model.MemberRole;
import com.jydev.mindtravel.service.mind.travel.model.Mood;
import com.jydev.mindtravel.service.mind.travel.model.MoodRecordRequest;
import com.jydev.mindtravel.service.mind.travel.service.MindTravelService;
import com.jydev.mindtravel.web.controller.MindTravelController;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

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
@WebMvcTest(MindTravelController.class)
public class MindTravelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HttpUtils httpUtils;

    @MockBean
    private MindTravelService mindTravelService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void recordMoodTest() throws Exception {
        MemberDto memberDto = MemberDto.builder()
                .memberIdx(0L)
                .email("test@naver.com")
                .nickname("nickname")
                .profileImgUrl("image-url")
                .createdDate(LocalDateTime.now())
                .role(MemberRole.USER)
                .build();
        MoodRecordRequest moodRecordRequest = MoodRecordRequest.builder()
                .mood(Mood.GOOD)
                .content("기분이 좋았습니다.")
                .build();
        String json = objectMapper.writeValueAsString(moodRecordRequest);
        given(httpUtils.makeEmptyResponse()).willReturn(
                ResponseEntity.ok(new HttpResponse<EmptyResponse>(HttpServletResponse.SC_OK, "", null))
        );

        ResultActions resultActions = this.mockMvc.perform(
                post("/v1/mind/travel/record-mood")
                        .header("Authorization", "Bearer Token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("member", memberDto)
                        .content(json)
        );
        resultActions.andExpect(status().isOk())
                .andDo(document("record-mood",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")
                        ),
                        requestFields(
                                fieldWithPath("content").description("글 내용"),
                                fieldWithPath("mood").type(JsonFieldType.STRING)
                                        .description("기분은 : BAD, A_LITTLE_BAD, NORMAL, GOOD 이렇게 구성 되어있습니다.")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )));
    }
}
