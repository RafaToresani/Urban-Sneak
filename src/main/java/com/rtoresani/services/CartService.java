package com.rtoresani.services;

import com.rtoresani.dtos.responses.CartResponse;

public interface CartService {
    CartResponse getCart(String email);
}
