package com.jydev.mindtravel.service.store.model;

import com.jydev.mindtravel.service.exception.PaymentFailException;

import java.util.Arrays;

public enum PaymentType {
    KAKAO_PAY;

    public static PaymentType findPaymentType(String type) {
        return Arrays.stream(values())
                .filter(t -> t.name().equals(type.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new PaymentFailException("결제 타입 오류"));
    }
}
