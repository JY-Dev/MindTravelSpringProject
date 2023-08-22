package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.domain.payment.dto.PaymentDto;
import com.jydev.mindtravel.domain.store.dto.StoreItemResponse;
import com.jydev.mindtravel.domain.store.service.StoreService;
import com.jydev.mindtravel.web.annotation.Payment;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/store")
public class StoreController {
    private final StoreService storeService;
    private final HttpUtils httpUtils;

    @GetMapping("/items")
    public HttpResponse<List<StoreItemResponse>> getStoreItems() {
        List<StoreItemResponse> storeItems = storeService.getStoreItems();
        return httpUtils.makeHttpResponse("", storeItems);
    }

    @PostMapping("/purchase/{itemId}")
    public HttpResponse<EmptyResponse> purchaseItem(@PathVariable Long itemId,
                                                    @Payment PaymentDto paymentDto) {
        storeService.purchaseStoreItem(itemId, paymentDto);
        return httpUtils.makeEmptyResponse();
    }

}
