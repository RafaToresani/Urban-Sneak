package com.rtoresani.services.impl;

import com.rtoresani.dtos.requests.InventoryRequest;
import com.rtoresani.entities.inventory.Inventory;
import com.rtoresani.entities.product.Product;
import com.rtoresani.exceptions.ResourceNotFoundException;
import com.rtoresani.repositories.inventory.InventoryRepository;
import com.rtoresani.repositories.product.ProductRepository;
import com.rtoresani.services.InventoryService;
import com.rtoresani.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void updateStock(InventoryRequest request) {
        Optional<Product> optionalProduct = productRepository.findProductBySkuCodeAndColorAndSize(request.skuCode(), request.color(), request.size());
        if(optionalProduct.isEmpty())
            throw new ResourceNotFoundException("ERROR: Product with SKU CODE '" + request.skuCode() + "', color '" + request.color() + "', and size '" + request.size() + "' doesn't exists.");

        Optional<Inventory> optionalInventory = this.inventoryRepository.findByProductSkuCodeAndColorAndSize(request.skuCode(), request.color(), request.size());
        Inventory updatedInventory = new Inventory();
        optionalInventory.ifPresent(inventory -> updatedInventory.setId(inventory.getId()));

        updatedInventory.setProduct(optionalProduct.get());
        updatedInventory.setSize(request.size());
        updatedInventory.setColor(request.color());
        updatedInventory.setQuantity(request.quantity());
        this.inventoryRepository.save(updatedInventory);

    }

    @Override
    public Boolean isInStock(String skuCode, String color, String size) {
        return this.inventoryRepository.existsBySkuCodeAndColorAndSize(skuCode, color, size);
    }

    @Override
    public Integer getQuantity(String skuCode, String color, String size) {
        Optional<Inventory> optionalInventory = this.inventoryRepository.findByProductSkuCodeAndColorAndSize(skuCode, color, size);
        if(optionalInventory.isEmpty()) throw new ResourceNotFoundException("ERROR: Product with SKU CODE '" + skuCode + "', color '" + color + "', and size '" + size + "' doesn't exists.");

        return optionalInventory.get().getQuantity();
    }
}
