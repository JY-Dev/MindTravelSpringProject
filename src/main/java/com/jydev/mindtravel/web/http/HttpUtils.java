package com.jydev.mindtravel.web.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class HttpUtils {
    private final ObjectMapper objectMapper;

    public <T> void sendResponse(HttpServletResponse response, int code, String message, T data) throws IOException {
        HttpResponse<T> httpResponse = new HttpResponse<>(message, data);
        String json = objectMapper.writeValueAsString(httpResponse);
        response.setStatus(code);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }

    public HttpResponse<EmptyResponse> makeEmptyResponse() {
        return makeHttpResponse("", new EmptyResponse());
    }

    public <R> HttpResponse<R> makeHttpResponse(String message, R data) {
        return new HttpResponse<>(message, data);
    }
}
