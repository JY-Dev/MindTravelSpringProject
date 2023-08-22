package com.jydev.mindtravel.domain.store.repository;

import com.jydev.mindtravel.domain.store.domain.StoreItem;

import java.util.List;

public interface StoreItemQueryRepository {
    List<StoreItem> getStoreItems();
}
