package com.rtoresani.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ProductRequest(
        @NotBlank(message = "SKU code is mandatory") String skuCode,
        @NotBlank(message = "Name is mandatory") String name,
        @NotBlank(message = "Description is mandatory") String description,
        @NotNull(message = "Category is mandatory") String category,
        @NotBlank(message = "Brand is mandatory") String brand,
        @NotNull(message = "Price is mandatory") @Positive(message = "Price must be positive") Double price,
        @Size(min = 1, message = "At least one size must be provided") Set<@NotBlank(message = "Size cannot be blank") String> sizes,
        @Size(min = 1, message = "At least one color must be provided") Set<@NotBlank(message = "Color cannot be blank") String> colors,
        String material) {
}
