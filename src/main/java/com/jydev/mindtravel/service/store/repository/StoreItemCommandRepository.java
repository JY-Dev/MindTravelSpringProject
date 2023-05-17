package com.jydev.mindtravel.service.store.repository;

import com.jydev.mindtravel.service.store.domain.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemCommandRepository extends JpaRepository<StoreItem,Long> {
}
