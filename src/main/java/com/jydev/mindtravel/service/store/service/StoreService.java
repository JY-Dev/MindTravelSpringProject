package com.jydev.mindtravel.service.store.service;

import com.jydev.mindtravel.service.store.model.StoreItemResponse;
import com.jydev.mindtravel.service.store.repository.StoreItemCommandRepository;
import com.jydev.mindtravel.service.store.repository.StoreItemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreItemQueryRepository queryRepository;
    private final StoreItemCommandRepository repository;
    public List<StoreItemResponse> getStoreItems(){
        return queryRepository.getStoreItems()
                .stream().map(StoreItemResponse::new)
                .toList();
    }
}
