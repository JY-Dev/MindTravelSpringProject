package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.service.payment.model.PaymentDto;
import com.jydev.mindtravel.web.annotation.Payment;
import com.jydev.mindtravel.service.store.model.StoreItemResponse;
import com.jydev.mindtravel.service.store.service.StoreService;
import com.jydev.mindtravel.web.http.EmptyResponse;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/store")
public class StoreController {
    private final StoreService storeService;
    private final HttpUtils httpUtils;
    @GetMapping("/items")
    public ResponseEntity<HttpResponse<List<StoreItemResponse>>> getStoreItems(){
        List<StoreItemResponse> storeItems = storeService.getStoreItems();
        return httpUtils.makeHttpResponse(HttpStatus.OK.value(),"",storeItems);
    }

    @PostMapping("/purchase/{itemId}")
    public ResponseEntity<HttpResponse<EmptyResponse>> purchaseItem(@PathVariable Long itemId,
                                                                    @Payment PaymentDto paymentDto){
        storeService.purchaseStoreItem(itemId,paymentDto);
        return httpUtils.makeEmptyResponse();
    }

}
