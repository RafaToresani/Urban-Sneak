package com.rtoresani.dtos.responses;

public record CartItemResponse(
        String skuCode,
        String size,
        String color,
        Integer quantity,
        Double price
) {
}
