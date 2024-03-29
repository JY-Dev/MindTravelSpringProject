package com.jydev.mindtravel.domain.store.domain;

import com.jydev.mindtravel.common.base.BaseEntity;
import com.jydev.mindtravel.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class StoreOrder extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "store_order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer quantity;

    private Integer price;

    private String paymentId;

    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(value = EnumType.STRING)
    private StoreOrderStatus status;
}
