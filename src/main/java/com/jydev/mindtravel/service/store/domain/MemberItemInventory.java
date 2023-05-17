package com.jydev.mindtravel.service.store.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import com.jydev.mindtravel.service.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class MemberItemInventory extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_inventory_id")
    private Long id;
    private Integer stockQuantity;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Version
    private Long version;
}
