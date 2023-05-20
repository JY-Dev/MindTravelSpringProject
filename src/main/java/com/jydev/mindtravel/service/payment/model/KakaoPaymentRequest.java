package com.jydev.mindtravel.service.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// Test 용도로만 진행되기 떄문에 tid랑 pgToken 제외하고는 하드 코딩
@Getter
public class KakaoPaymentRequest {
    private String tid;
    private String cid = "TC0ONETIME";

    @JsonProperty("partner_order_id")
    private String orderId = "test";
    @JsonProperty("partner_user_id")
    private String userId = "test";
    @JsonProperty("pg_token")
    private String pgToken;

    public KakaoPaymentRequest(KakaoPaymentDto kakaoPaymentDTO){
        this.tid = kakaoPaymentDTO.getTid();
        this.pgToken = kakaoPaymentDTO.getPgToken();
    }
}
