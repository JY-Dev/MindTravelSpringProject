package com.jydev.mindtravel.service.store.model;

import com.jydev.mindtravel.service.store.domain.StoreItem;
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

    public StoreItemResponse(StoreItem storeItem){
        this.price = storeItem.getPrice();
        this.quantity = storeItem.getQuantity();
        this.item = new ItemResponse(storeItem.getItem());
    }
}
