package com.jydev.mindtravel.service.payment.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoPaymentDto extends PaymentDto {
    private String tid;
    private String pgToken;
}
