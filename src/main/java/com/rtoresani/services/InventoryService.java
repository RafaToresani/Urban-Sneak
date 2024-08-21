package com.rtoresani.services;

import com.rtoresani.dtos.requests.InventoryRequest;
import org.apache.coyote.BadRequestException;

public interface InventoryService {
    void updateStock(InventoryRequest request);

    Boolean isInStock(String skuCode, String color, String size);

    Integer getQuantity(String skuCode, String color, String size);

    void checkAndReserveProduct(String skuCode, String size, String color, Integer quantity) throws BadRequestException;
}
