package com.jydev.mindtravel.domain.payment.service;

import com.jydev.mindtravel.domain.payment.dto.PaymentDto;
import com.jydev.mindtravel.domain.store.domain.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PaymentCompositeService {
    private final KakaoPaymentService kakaoPaymentService;

    public void payment(PaymentDto paymentDto) {
        PaymentType type = paymentDto.getPaymentType();
        if (Objects.requireNonNull(type) == PaymentType.KAKAO_PAY) {
            kakaoPaymentService.payment(paymentDto);
        }
    }
}
