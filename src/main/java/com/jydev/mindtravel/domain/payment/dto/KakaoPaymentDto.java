package com.jydev.mindtravel.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoPaymentDto extends PaymentDto {
    private String tid;
    private String pgToken;
}
