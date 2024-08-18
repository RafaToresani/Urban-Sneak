package com.rtoresani.dtos.responses;

import com.rtoresani.entities.product.ECategory;

import java.util.Set;

public record ProductResponse(
        String skuCode,
        String name,
        String description,
        ECategory category,
        String brand,
        Double price,
        Set<String> sizes,
        Set<String> colors,
        String material,
        Boolean isActive
) {
}
