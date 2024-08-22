package com.rtoresani.controllers;

import com.rtoresani.dtos.requests.InventoryRequest;
import com.rtoresani.dtos.responses.InventoryResponse;
import com.rtoresani.services.InventoryService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    //      P U T
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public void updateStock(@RequestBody @Valid InventoryRequest request, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        this.inventoryService.updateStock(request);
    }

    //     G E T
    @GetMapping("/is-in-stock")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER', 'MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInStock(@RequestParam("sku-code") String skuCode, @RequestParam String color, @RequestParam String size){
        return this.inventoryService.isInStock(skuCode, color, size);
    }

    @GetMapping("/quantity")
    @ResponseStatus(HttpStatus.OK)
    public Integer getQuantity(@RequestParam("sku-code") String skuCode, @RequestParam String color, @RequestParam String size){
        return this.inventoryService.getQuantity(skuCode, color, size);
    }

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public List<InventoryResponse> findAllInventoriesBySkuCode(@PathVariable(name = "sku-code") String skuCode){
        return this.inventoryService.findAllInventories(skuCode);
    }
}
