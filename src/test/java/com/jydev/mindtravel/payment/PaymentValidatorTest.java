package com.jydev.mindtravel.payment;

import com.jydev.mindtravel.service.exception.PaymentFailException;
import com.jydev.mindtravel.service.payment.utils.PaymentValidator;
import com.jydev.mindtravel.service.payment.model.KakaoPaymentDto;
import com.jydev.mindtravel.service.payment.model.PaymentDto;
import com.jydev.mindtravel.service.store.model.PaymentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PaymentValidatorTest {
    private PaymentValidator validator = new PaymentValidator();

    @DisplayName("PaymentType이 KakaoPay인 경우에는 KakaoPaymentDto로 형 변환이 가능해야한다.")
    @Test
    void validationTypeCastTest(){
        PaymentDto paymentDto = mock(KakaoPaymentDto.class);
        given(paymentDto.getPaymentType()).willReturn(PaymentType.KAKAO_PAY);
        validator.validationPaymentDtoTypeCast(paymentDto);
    }

    @DisplayName("PaymentDto가 KakaoPaymentDto가 아닐때 PaymentType이 KakaoPay인 경우에는 PaymentFailException이 발생해야합니다")
    @Test
    void validationTypeCastFailTest(){
        PaymentDto paymentDto = mock(PaymentDto.class);
        given(paymentDto.getPaymentType()).willReturn(PaymentType.KAKAO_PAY);
        Assertions.assertThrows(PaymentFailException.class,() -> validator.validationPaymentDtoTypeCast(paymentDto));
    }
}
