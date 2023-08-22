package com.jydev.mindtravel.domain.store.domain;

import com.jydev.mindtravel.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ItemType type;

    private String name;
}
