package com.rtoresani.services;

import com.rtoresani.dtos.requests.CartItemRequest;
import com.rtoresani.dtos.responses.CartResponse;
import org.apache.coyote.BadRequestException;

public interface CartService {
    CartResponse getCart(String email);

    CartResponse addToCart(CartItemRequest itemRequest, String email) throws BadRequestException;

    void cleanCart(String email);

    void deleteItemFromCart(Long itemId, String email);

    void updateQuantity(Long itemId, Integer quantity);
}
