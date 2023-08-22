package com.jydev.mindtravel.domain.payment.dto;

import com.jydev.mindtravel.domain.store.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaymentDto {
    private PaymentType paymentType;
}
