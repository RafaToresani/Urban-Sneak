package com.rtoresani.services.impl;

import com.rtoresani.dtos.requests.InventoryRequest;
import com.rtoresani.entities.inventory.Inventory;
import com.rtoresani.entities.product.Product;
import com.rtoresani.entities.product.ProductColor;
import com.rtoresani.entities.product.ProductSize;
import com.rtoresani.exceptions.ResourceAlreadyExistsException;
import com.rtoresani.exceptions.ResourceNotFoundException;
import com.rtoresani.repositories.inventory.InventoryRepository;
import com.rtoresani.repositories.product.ProductRepository;
import com.rtoresani.services.InventoryService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
    public void createInventories(Product product) {
        product.getColors().forEach(color -> {
            product.getSizes().forEach(size -> {
                this.createInventory(product, product.getSkuCode(), size.getSize(), color.getColor());
            });
        });
    }

    private void createInventory(Product product, String skuCode, String size, String color) {
        Optional<Inventory> inventory = this.inventoryRepository.findByProductSkuCodeAndColorAndSize(skuCode, color, size);
        if(inventory.isPresent())
            throw new ResourceAlreadyExistsException("ERROR: Product with SKU CODE '" + skuCode + "', color '" + color + "', and size '" + size + "' already exists.");

        Inventory newInv = Inventory
                .builder()
                .product(product)
                .color(color)
                .size(size)
                .quantity(0)
                .build();

        this.inventoryRepository.save(newInv);
    }



    @Override
    public Boolean isInStock(String skuCode, String color, String size) {
        return this.inventoryRepository.existsByProductSkuCodeAndColorAndSize(skuCode, color, size);
    }

    @Override
    public Integer getQuantity(String skuCode, String color, String size) {
        Optional<Inventory> optionalInventory = this.inventoryRepository.findByProductSkuCodeAndColorAndSize(skuCode, color, size);
        if(optionalInventory.isEmpty()) throw new ResourceNotFoundException("ERROR: Product with SKU CODE '" + skuCode + "', color '" + color + "', and size '" + size + "' doesn't exists.");
        return optionalInventory.get().getQuantity();
    }

    @Override
    public void checkAndReserveProduct(String skuCode, String size, String color, Integer quantity) throws BadRequestException {
        Optional<Inventory> optionalInventory = this.inventoryRepository.findByProductSkuCodeAndColorAndSize(skuCode, color, size);
        if(optionalInventory.isEmpty()) throw new ResourceNotFoundException("ERROR: Product with SKU CODE '" + skuCode + "', color '" + color + "', and size '" + size + "' doesn't exists.");
        if(optionalInventory.get().getQuantity()<quantity) throw new BadRequestException("The product doesn't have enough stock");
        Inventory inventory = optionalInventory.get();
        inventory.setQuantity(inventory.getQuantity()-quantity);
        this.inventoryRepository.save(inventory);
    }

    @Override
    public void increaseInventoryQuantity(String skuCode, String color, String size, Integer quantity) {
        Optional<Inventory> optionalInventory = this.inventoryRepository.findByProductSkuCodeAndColorAndSize(skuCode, color, size);
        if(optionalInventory.isEmpty()) throw new ResourceNotFoundException("ERROR: Product with SKU CODE '" + skuCode + "', color '" + color + "', and size '" + size + "' doesn't exists.");
        Inventory inventory = optionalInventory.get();
        inventory.setQuantity(inventory.getQuantity()+quantity);
        this.inventoryRepository.save(inventory);
    }

    @Override
    public void decreaseInventoryQuantity(String skuCode, String size, String color, Integer quantity) {
        Optional<Inventory> optionalInventory = this.inventoryRepository.findByProductSkuCodeAndColorAndSize(skuCode, color, size);
        if(optionalInventory.isEmpty()) throw new ResourceNotFoundException("ERROR: Product with SKU CODE '" + skuCode + "', color '" + color + "', and size '" + size + "' doesn't exists.");
        Inventory inventory = optionalInventory.get();
        inventory.setQuantity(inventory.getQuantity()-quantity);
        this.inventoryRepository.save(inventory);
    }


}
