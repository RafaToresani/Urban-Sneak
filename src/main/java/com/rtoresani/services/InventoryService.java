package com.rtoresani.services;

import com.rtoresani.dtos.requests.InventoryRequest;
import com.rtoresani.entities.product.Product;
import com.rtoresani.entities.product.ProductColor;
import com.rtoresani.entities.product.ProductSize;
import org.apache.coyote.BadRequestException;

import java.util.Set;

public interface InventoryService {
    void updateStock(InventoryRequest request);

    Boolean isInStock(String skuCode, String color, String size);

    Integer getQuantity(String skuCode, String color, String size);

    void checkAndReserveProduct(String skuCode, String size, String color, Integer quantity) throws BadRequestException;

    void increaseInventoryQuantity(String skuCode, String color, String size, Integer quantity);

    void decreaseInventoryQuantity(String skuCode, String size, String color, Integer quantity);

    void createInventories(Product product);
}
