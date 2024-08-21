package com.rtoresani.dtos.responses;

import java.util.Set;

public record CartResponse(
        Set<CartItemResponse> items
) {
}
