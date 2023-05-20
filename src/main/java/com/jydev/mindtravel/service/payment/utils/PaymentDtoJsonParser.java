package com.jydev.mindtravel.service.payment.utils;

import com.jydev.mindtravel.service.payment.model.KakaoPaymentDto;
import com.jydev.mindtravel.service.payment.model.PaymentDto;
import com.jydev.mindtravel.service.store.model.PaymentType;
import org.springframework.stereotype.Component;

import java.util.Map;

public class PaymentDtoJsonParser {
    public PaymentDto parser(Map<String, Object> paymentDtoJson){
        PaymentType paymentType = PaymentType.findPaymentType((String)paymentDtoJson.get("paymentType"));
        PaymentDto paymentDto = switch (paymentType){
            case KAKAO_PAY -> parseKakaoPaymentDto(paymentDtoJson);
        };
        paymentDto.setPaymentType(paymentType);
        return paymentDto;
    }

    private PaymentDto parseKakaoPaymentDto(Map<String, Object> json){
        String tid = (String) json.get("tid");
        String pgToken = (String) json.get("pgToken");
        return KakaoPaymentDto.builder()
                .tid(tid)
                .pgToken(pgToken)
                .build();
    }
}
