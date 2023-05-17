package com.jydev.mindtravel.web.controller;

import com.jydev.mindtravel.service.store.model.StoreItemResponse;
import com.jydev.mindtravel.service.store.service.StoreService;
import com.jydev.mindtravel.web.http.HttpResponse;
import com.jydev.mindtravel.web.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/store")
public class StoreController {
    private final StoreService storeService;
    private final HttpUtils httpUtils;
    @GetMapping
    public ResponseEntity<HttpResponse<List<StoreItemResponse>>> getStoreItems(){
        List<StoreItemResponse> storeItems = storeService.getStoreItems();
        return httpUtils.makeHttpResponse(HttpStatus.OK.value(),"",storeItems);
    }
}
