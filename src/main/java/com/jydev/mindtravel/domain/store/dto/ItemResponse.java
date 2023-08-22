package com.jydev.mindtravel.domain.store.dto;

import com.jydev.mindtravel.domain.store.domain.Item;
import com.jydev.mindtravel.domain.store.domain.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ItemResponse {
    private Long id;
    private ItemType type;
    private String name;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.type = item.getType();
        this.name = item.getName();
    }
}
