package com.jydev.mindtravel.service.payment.service;

import com.jydev.mindtravel.service.payment.model.KakaoPaymentDto;
import com.jydev.mindtravel.service.payment.model.KakaoPaymentRequest;
import com.jydev.mindtravel.service.payment.model.PaymentDto;
import com.jydev.mindtravel.service.payment.utils.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoPaymentService {
    private final RestTemplate restTemplate;
    private final PaymentValidator validator;
    private String url = "https://kapi.kakao.com/v1/payment/approve";
    @Value("${kakao.admin-key}")
    private String adminKey;

    public void payment(PaymentDto dto) {
        validator.validationPaymentDtoTypeCast(dto);
        KakaoPaymentDto kakaoPaymentDto = (KakaoPaymentDto) dto;
        HttpHeaders headers = getHttpHeaders();
        KakaoPaymentRequest request = new KakaoPaymentRequest(kakaoPaymentDto);
        MultiValueMap<String, String> param = getParam(request);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(param, headers);
        ResponseEntity response = restTemplate.postForEntity(url, requestHttpEntity, String.class);
        validator.validationPaymentResponse(response);
    }

    private static MultiValueMap<String, String> getParam(KakaoPaymentRequest request) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // 파라미터 추가 예시
        params.add("partner_order_id", request.getOrderId());
        params.add("partner_user_id", request.getUserId());
        params.add("tid", request.getTid());
        params.add("cid", request.getCid());
        params.add("pg_token", request.getPgToken());
        return params;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "KakaoAK " + adminKey);
        return headers;
    }
}
