package com.jydev.mindtravel.web.filter;

import com.jydev.mindtravel.web.utils.ReReadableRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MDC.put("traceId", UUID.randomUUID().toString());
        doFilterWrapped(request, response, filterChain);
        MDC.clear();
    }

    public void doFilterWrapped(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest requestWrapper = getWrappedRequest(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            logRequest(requestWrapper);
            doFilter(requestWrapper, responseWrapper, filterChain);
        } finally {
            logResponse(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    public void logRequest(HttpServletRequest request) throws IOException {
        String queryString = request.getQueryString();
        String prefix = "Request";
        log.info("Request : {} uri=[{}] content-type=[{}]",
                request.getMethod(),
                queryString == null ? request.getRequestURI() : request.getRequestURI() + queryString,
                request.getContentType()
        );
        if(request.getContentType() == null)
            return;
        if(request instanceof ReReadableRequestWrapper){
            logPayload(prefix,request.getInputStream());
        } else{
            logPayload(prefix, request.getParameterMap());
        }

    }

    public void logResponse(ContentCachingResponseWrapper response) throws IOException {
        logPayload("Response", response.getContentInputStream());
    }

    private void logPayload(String prefix, InputStream inputStream) throws IOException {
        byte[] content = StreamUtils.copyToByteArray(inputStream);
        if (content.length > 0) {
            String contentString = new String(content);
            log.info("{} Payload: {}", prefix, contentString);
        }
    }

    public HttpServletRequest getWrappedRequest(HttpServletRequest request) throws IOException {
        if(request.getContentType() != null && !needParameterPayload(request.getContentType()))
            return new ReReadableRequestWrapper(request);
        else
            return request;
    }

    private boolean needParameterPayload(String contentType) {
        return (contentType.equalsIgnoreCase(MediaType.APPLICATION_FORM_URLENCODED_VALUE) ||
                contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE));
    }

    private void logPayload(String prefix, Map<String,String[]> parameter) throws IOException {
        log.info("{} Payload: {}", prefix, getParameterString(parameter));
    }

    private String getParameterString(Map<String,String[]> parameter){
        return parameter.entrySet().stream()
                .map(e -> "["+e.getKey() + " : " + String.join(", ",e.getValue())+"]")
                .collect(Collectors.joining(", "));
    }

}
