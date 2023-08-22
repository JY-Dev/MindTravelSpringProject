package com.jydev.mindtravel.domain.store.dto;

import com.jydev.mindtravel.domain.store.domain.StoreItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class StoreItemResponse {
    private Integer price;
    private Integer quantity;
    private ItemResponse item;

    public StoreItemResponse(StoreItem storeItem) {
        this.price = storeItem.getPrice();
        this.quantity = storeItem.getQuantity();
        this.item = new ItemResponse(storeItem.getItem());
    }
}
