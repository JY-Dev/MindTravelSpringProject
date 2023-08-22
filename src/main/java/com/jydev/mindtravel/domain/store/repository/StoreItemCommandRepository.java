package com.jydev.mindtravel.domain.store.repository;

import com.jydev.mindtravel.domain.store.domain.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemCommandRepository extends JpaRepository<StoreItem, Long> {
}
