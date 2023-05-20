package com.jydev.mindtravel.service.payment.service;

import com.jydev.mindtravel.service.payment.model.KakaoPaymentDto;
import com.jydev.mindtravel.service.payment.model.PaymentDto;
import com.jydev.mindtravel.service.store.model.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentCompositeService {
    private final KakaoPaymentService kakaoPaymentService;

    public void payment(PaymentDto paymentDto) {
        PaymentType type = paymentDto.getPaymentType();
        switch (type) {
            case KAKAO_PAY -> {
                KakaoPaymentDto dto = (KakaoPaymentDto) paymentDto;
                kakaoPaymentService.payment(dto);
            }
        }
    }
}
