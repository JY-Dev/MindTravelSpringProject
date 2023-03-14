package com.jydev.mindtravel.member;


import com.jydev.mindtravel.ControllerTest;
import com.jydev.mindtravel.member.model.MemberDto;
import com.jydev.mindtravel.member.model.MemberRole;
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

import static com.jydev.mindtravel.ApiDocumentUtils.getDocumentRequest;
import static com.jydev.mindtravel.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
@WebMvcTest(MemberController.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HttpUtils httpUtils;

    @Test
    public void getMember() throws Exception {
        //Given
        MemberDto memberDto = MemberDto.builder()
                .memberIdx(0L)
                .email("test@naver.com")
                .nickname("nickname")
                .profileImgUrl("image-url")
                .role(MemberRole.USER)
                .build();
        given(httpUtils.makeHttpResponse(eq(200), eq(""), any(MemberDto.class))).willReturn(
                ResponseEntity.ok(new HttpResponse<>(200, "", memberDto)
        ));

        //when
        ResultActions result = this.mockMvc.perform(
                get("/v1/member")
                        .header("Authorization", "Bearer Token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("member",memberDto)
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
                                fieldWithPath("data.role").type(JsonFieldType.STRING).description("권한")
                        )));
    }
}