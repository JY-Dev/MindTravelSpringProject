package com.jydev.mindtravel.member;


import com.jydev.mindtravel.ControllerTest;
import com.jydev.mindtravel.service.member.model.MemberDto;
import com.jydev.mindtravel.service.member.model.MemberRole;
import com.jydev.mindtravel.service.member.service.MemberService;
import com.jydev.mindtravel.web.controller.MemberController;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
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
import static com.jydev.mindtravel.util.ControllerTestHelper.baseRequestBuilder;
import static com.jydev.mindtravel.util.ControllerTestHelper.memberDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.formParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
@WebMvcTest(MemberController.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HttpUtils httpUtils;

    @MockBean
    private MemberService memberService;

    @Test
    public void getMember() throws Exception {
        //Given
        given(httpUtils.makeHttpResponse(eq(200), eq(""), any(MemberDto.class))).willReturn(
                ResponseEntity.ok(new HttpResponse<>(200, "", memberDto)
        ));

        //when
        ResultActions result = this.mockMvc.perform(
                baseRequestBuilder(
                        get("/v1/member")
                )
        );
        result.andExpect(status().isOk())
                .andDo(document("get-member", getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.memberIdx").type(JsonFieldType.NUMBER).description("memberIdx"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("data.profileImgUrl").type(JsonFieldType.STRING).description("프로필 이미지 Url"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING).description("권한"),
                                fieldWithPath("data.createdDate").type(JsonFieldType.STRING).description("생성 날짜")
                        )));
    }

    @Test
    public void editNickname() throws Exception {
        //Given
        String changeNickname= "changeNickname";
        given(memberService.editNickname(any(String.class),any(String.class))).willReturn(memberDto);
        given(httpUtils.makeHttpResponse(eq(200), eq(""), any(MemberDto.class))).willReturn(
                ResponseEntity.ok(new HttpResponse<>(200, "", memberDto)
                ));
        ResultActions result = this.mockMvc.perform(
                patch("/v1/member/nickname")
                        .header("Authorization", "Bearer Token")
                        .requestAttr("member",memberDto)
                        .param("nickname",changeNickname)
        );
        result.andExpect(status().isOk())
                .andDo(document("edit-nickname", getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")
                        ),
                        formParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.memberIdx").type(JsonFieldType.NUMBER).description("memberIdx"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("data.profileImgUrl").type(JsonFieldType.STRING).description("프로필 이미지 Url"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING).description("권한"),
                                fieldWithPath("data.createdDate").type(JsonFieldType.STRING).description("생성 날짜")
                        )));
    }
}
