package com.jydev.mindtravel.mindtravel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.ControllerTest;
import com.jydev.mindtravel.domain.mind.travel.domain.Mood;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordRequest;
import com.jydev.mindtravel.domain.mind.travel.dto.MoodRecordResponse;
import com.jydev.mindtravel.domain.mind.travel.service.MindTravelService;
import com.jydev.mindtravel.util.ControllerTestHelper;
import com.jydev.mindtravel.web.controller.MindTravelController;
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
import java.util.List;

import static com.jydev.mindtravel.ApiDocumentUtils.getDocumentRequest;
import static com.jydev.mindtravel.ApiDocumentUtils.getDocumentResponse;
import static com.jydev.mindtravel.util.ControllerTestHelper.baseRequestBuilder;
import static com.jydev.mindtravel.util.ControllerTestHelper.memberDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
        MoodRecordRequest moodRecordRequest = MoodRecordRequest.builder()
                .mood(Mood.GOOD)
                .content("기분이 좋았습니다.")
                .build();
        String json = objectMapper.writeValueAsString(moodRecordRequest);
        given(httpUtils.makeEmptyResponse()).willReturn(
                new HttpResponse<>("", null)
        );

        ResultActions resultActions = this.mockMvc.perform(
                ControllerTestHelper.baseRequestBuilder(
                        post("/v1/mind/travel/record-mood")
                                .content(json)
                )
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
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )));
    }

    @Test
    public void getRecordMoods() throws Exception {
        MoodRecordResponse moodRecordResponse = MoodRecordResponse.builder()
                .moodRecordId(0L)
                .mood(Mood.GOOD)
                .content("content")
                .createdDate(LocalDateTime.now()).build();
        List<MoodRecordResponse> moodRecordResponses = List.of(moodRecordResponse);
        given(mindTravelService.getRecordMoods(any(String.class), any(String.class))).willReturn(moodRecordResponses);
        given(httpUtils.makeHttpResponse(any(String.class), any(List.class))).willReturn(
                new HttpResponse<>("", moodRecordResponses)
        );
        ResultActions resultActions = this.mockMvc.perform(
                baseRequestBuilder(
                        get("/v1/mind/travel/record-moods")
                                .queryParam("date","2023-03-02")
                )
        );
        resultActions.andExpect(status().isOk())
                .andDo(document("get-record-moods",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")
                        ),
                        queryParameters(
                                parameterWithName("date").description("조회 날짜")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data[].moodRecordId").description("아이디").type(JsonFieldType.NUMBER),
                                fieldWithPath("data[].content").description("글 내용").type(JsonFieldType.STRING),
                                fieldWithPath("data[].mood").type(JsonFieldType.STRING)
                                        .description("기분은 : BAD, A_LITTLE_BAD, NORMAL, GOOD 이렇게 구성 되어있습니다."),
                                fieldWithPath("data[].createdDate").type(JsonFieldType.STRING)
                                        .description("작성 날짜 및 시간")
                        )));
    }

    @Test
    public void deleteRecordMood() throws Exception {

        given(httpUtils.makeEmptyResponse()).willReturn(
                new HttpResponse<EmptyResponse>("", null)
        );

        ResultActions resultActions = this.mockMvc.perform(
                delete("/v1/mind/travel/record-mood/{moodRecordId}","0")
                        .header("Authorization", "Bearer Token")
                        .requestAttr("member", memberDto)
        );
        resultActions.andExpect(status().isOk())
                .andDo(document("delete-record-mood",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")
                        ),
                        pathParameters(
                                parameterWithName("moodRecordId").description("RecordMood 아이디")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )));
    }
}
