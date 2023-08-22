package com.jydev.mindtravel.service.store.model;

import com.jydev.mindtravel.service.store.domain.Item;
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
    public ItemResponse(Item item){
        this.id = item.getId();
        this.type = item.getType();
        this.name = item.getName();
    }
}
