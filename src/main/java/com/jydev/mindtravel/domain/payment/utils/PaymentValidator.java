package com.jydev.mindtravel.domain.payment.utils;

import com.jydev.mindtravel.common.exception.PaymentFailException;
import com.jydev.mindtravel.domain.payment.dto.KakaoPaymentDto;
import com.jydev.mindtravel.domain.payment.dto.PaymentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {
    public void validationPaymentDtoTypeCast(PaymentDto dto) {
        Class<?> type = switch (dto.getPaymentType()) {
            case KAKAO_PAY -> KakaoPaymentDto.class;
        };
        if (!type.isInstance(dto)) {
            throw new PaymentFailException("결제 타입이 올바르지 않습니다.");
        }
    }

    public void validationPaymentResponse(ResponseEntity paymentResponse) {
        if (paymentResponse.getStatusCode() != HttpStatus.OK) {
            throw new PaymentFailException("결제 요청 실패");
        }
    }
}
