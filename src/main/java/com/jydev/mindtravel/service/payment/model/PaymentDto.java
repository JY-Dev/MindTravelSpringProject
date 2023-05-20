package com.jydev.mindtravel.service.payment.model;

import com.jydev.mindtravel.service.store.model.PaymentType;
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
