package com.jydev.mindtravel.domain.payment.utils;

import com.jydev.mindtravel.domain.payment.dto.KakaoPaymentDto;
import com.jydev.mindtravel.domain.payment.dto.PaymentDto;
import com.jydev.mindtravel.domain.store.domain.PaymentType;

import java.util.Map;

public class PaymentDtoJsonParser {
    public PaymentDto parse(Map<String, Object> paymentDtoJson) {
        PaymentType paymentType = PaymentType.findPaymentType((String) paymentDtoJson.get("paymentType"));
        PaymentDto paymentDto = switch (paymentType) {
            case KAKAO_PAY -> parseKakaoPaymentDto(paymentDtoJson);
        };
        paymentDto.setPaymentType(paymentType);
        return paymentDto;
    }

    private PaymentDto parseKakaoPaymentDto(Map<String, Object> json) {
        String tid = (String) json.get("tid");
        String pgToken = (String) json.get("pgToken");
        return KakaoPaymentDto.builder()
                .tid(tid)
                .pgToken(pgToken)
                .build();
    }
}
