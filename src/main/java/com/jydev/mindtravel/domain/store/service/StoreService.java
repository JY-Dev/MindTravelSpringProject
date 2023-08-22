package com.jydev.mindtravel.domain.store.service;

import com.jydev.mindtravel.domain.payment.dto.PaymentDto;
import com.jydev.mindtravel.domain.payment.service.PaymentCompositeService;
import com.jydev.mindtravel.domain.store.dto.StoreItemResponse;
import com.jydev.mindtravel.domain.store.repository.StoreItemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreItemQueryRepository queryRepository;
    private final PaymentCompositeService paymentService;

    public List<StoreItemResponse> getStoreItems() {
        return queryRepository.getStoreItems()
                .stream().map(StoreItemResponse::new)
                .toList();
    }

    public void purchaseStoreItem(Long itemId, PaymentDto paymentDto) {
        paymentService.payment(paymentDto);
    }
}
