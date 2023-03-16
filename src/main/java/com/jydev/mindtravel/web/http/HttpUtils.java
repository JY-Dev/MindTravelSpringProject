package com.jydev.mindtravel.web.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class HttpUtils {
    private final ObjectMapper objectMapper;
    public <T> void sendResponse(HttpServletResponse response, int code , String message, T data) throws IOException {
        HttpResponse<T> httpResponse = new HttpResponse<>(code,message,data);
        String json = objectMapper.writeValueAsString(httpResponse);
        response.setStatus(code);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }

    public ResponseEntity<HttpResponse<EmptyResponse>> makeEmptyResponse(){
        return makeHttpResponse(HttpServletResponse.SC_OK,"",new EmptyResponse());
    }

    public <R> ResponseEntity<HttpResponse<R>> makeHttpResponse(int code , String message, R data){
        HttpResponse<R> httpResponse = new HttpResponse<>(code,message,data);
        return ResponseEntity.status(code)
                .body(httpResponse);
    }
}
