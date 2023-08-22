package com.jydev.mindtravel.domain.store.repository;

import com.jydev.mindtravel.domain.store.domain.MemberItemInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberItemInventoryCommandRepository extends JpaRepository<MemberItemInventory, Long> {
}
