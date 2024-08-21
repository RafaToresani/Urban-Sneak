package com.rtoresani.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CartItemRequest(
        @NotNull(message = "SKU Code cannot be null")
        String skuCode,

        @NotNull(message = "Size cannot be null")
        String size,

        @NotNull(message = "Color cannot be null")
        @Size(min = 1, max = 15, message = "Color must be between 1 and 15 characters")
        String color,

        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be greater than zero")
        Double price
) {
}
