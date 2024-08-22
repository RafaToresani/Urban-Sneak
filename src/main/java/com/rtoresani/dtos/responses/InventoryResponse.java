package com.rtoresani.dtos.responses;

public record InventoryResponse(
        String color,
        String size,
        Integer quantity
) {
}
