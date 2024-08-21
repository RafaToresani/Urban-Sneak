package com.rtoresani.services.impl;

import com.rtoresani.dtos.responses.CartItemResponse;
import com.rtoresani.dtos.responses.CartResponse;
import com.rtoresani.entities.cart.Cart;
import com.rtoresani.entities.cart.CartItem;
import com.rtoresani.exceptions.ResourceNotFoundException;
import com.rtoresani.repositories.cart.CartItemRepository;
import com.rtoresani.repositories.cart.CartRepository;
import com.rtoresani.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository itemRepository;


    @Override
    public CartResponse getCart(String email) {
        Optional<Cart> cart = this.cartRepository.findByUserEmail(email);
        if(cart.isEmpty()) throw new ResourceNotFoundException("User doesn't have a cart");

        return cartToResponse(cart.get());
    }

    private CartResponse cartToResponse(Cart cart){
        Set<CartItemResponse> items = cart.getItems().stream().map(this::itemToResponse).collect(Collectors.toSet());
        return new CartResponse(items);
    }

    private CartItemResponse itemToResponse(CartItem item){
        return new CartItemResponse(item.getSkuCode(), item.getSize(), item.getColor(), item.getQuantity(), item.getPrice());
    }
}
