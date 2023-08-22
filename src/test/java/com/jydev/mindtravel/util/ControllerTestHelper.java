package com.jydev.mindtravel.util;

import com.jydev.mindtravel.domain.member.dto.MemberDto;
import com.jydev.mindtravel.domain.member.domain.MemberRole;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;


public class ControllerTestHelper {
    public static MemberDto memberDto = MemberDto.builder()
            .memberIdx(0L)
            .email("test@naver.com")
            .nickname("nickname")
            .profileImgUrl("image-url")
            .createdDate(LocalDateTime.now())
            .role(MemberRole.USER)
            .build();

    public static RequestBuilder baseRequestBuilder(MockHttpServletRequestBuilder otherBuilder){
        return otherBuilder
                .header("Authorization", "Bearer Token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .requestAttr("member", memberDto);
    }
}
