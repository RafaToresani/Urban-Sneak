package com.rtoresani.dtos.responses;

public record CartItemResponse(
        Long itemId,
        String skuCode,
        String size,
        String color,
        Integer quantity,
        Double price
) {
}
