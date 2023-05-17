package com.jydev.mindtravel.service.store.repository;

import com.jydev.mindtravel.service.store.domain.StoreItem;

import java.util.List;

public interface StoreItemQueryRepository {
    List<StoreItem> getStoreItems();
}
