package com.jydev.mindtravel.service.store.domain;

import com.jydev.mindtravel.service.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class StoreItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "store_item_id")
    private Long id;
    private Integer price;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
