package com.jydev.mindtravel.member;

import com.jydev.mindtravel.ControllerTest;
import com.jydev.mindtravel.jwt.Jwt;
import com.jydev.mindtravel.web.controller.AuthController;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HttpUtils httpUtils;

    @Test
    public void socialLoginTest() throws Exception {
        //given
        given(httpUtils.makeHttpResponse(eq(200), eq(""), any(Jwt.class)))
                .willReturn(
                        ResponseEntity.ok(new HttpResponse<>(200, "", new Jwt("accessToken", "refreshToken")))
                );

        //when
        ResultActions result = this.mockMvc.perform(
                post("/v1/login/oauth2/{oauthServerType}", "google")
                        .header("Authorization", "Bearer testToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isOk())
                .andDo(document("social-login", getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("oauthServerType").description("Oauth 종류")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Oauth Access Token")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("AccessToken"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("RefreshToken")
                        )));
    }
}
