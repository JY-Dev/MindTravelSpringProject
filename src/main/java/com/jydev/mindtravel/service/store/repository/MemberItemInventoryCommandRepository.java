package com.jydev.mindtravel.service.store.repository;

import com.jydev.mindtravel.service.store.domain.MemberItemInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberItemInventoryCommandRepository extends JpaRepository<MemberItemInventory,Long> {
}
