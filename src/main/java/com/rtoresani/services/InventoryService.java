package com.rtoresani.services;

import com.rtoresani.dtos.requests.InventoryRequest;

public interface InventoryService {
    void updateStock(InventoryRequest request);

    Boolean isInStock(String skuCode, String color, String size);

    Integer getQuantity(String skuCode, String color, String size);
}
