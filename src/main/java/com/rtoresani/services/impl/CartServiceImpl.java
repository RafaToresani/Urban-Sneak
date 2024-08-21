package com.rtoresani.services.impl;

import com.rtoresani.dtos.requests.CartItemRequest;
import com.rtoresani.dtos.responses.CartItemResponse;
import com.rtoresani.dtos.responses.CartResponse;
import com.rtoresani.entities.cart.Cart;
import com.rtoresani.entities.cart.CartItem;
import com.rtoresani.exceptions.ResourceNotFoundException;
import com.rtoresani.repositories.cart.CartItemRepository;
import com.rtoresani.repositories.cart.CartRepository;
import com.rtoresani.services.CartService;
import com.rtoresani.services.InventoryService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository itemRepository;
    @Autowired
    private InventoryService inventoryService;


    @Override
    public CartResponse getCart(String email) {
        Optional<Cart> cart = this.cartRepository.findByUserEmail(email);
        if(cart.isEmpty()) throw new ResourceNotFoundException("User doesn't have a cart");
        return cartToResponse(cart.get());
    }

    @Override
    public CartResponse addToCart(CartItemRequest itemRequest, String email) throws BadRequestException {
        Optional<Cart> cart = this.cartRepository.findByUserEmail(email);
        if(cart.isEmpty()) throw new ResourceNotFoundException("User doesn't have a cart");
        this.inventoryService.checkAndReserveProduct(itemRequest.skuCode(), itemRequest.size(), itemRequest.color(), itemRequest.quantity());
        CartItem cartItem = this.requestToCartItem(cart.get(), itemRequest);
        cart.get().getItems().add(cartItem);
        cart.get().setLastUpdate(LocalDateTime.now());
        return this.cartToResponse(this.cartRepository.save(cart.get()));
    }

    private CartItem requestToCartItem(Cart cart, CartItemRequest request) {
        return CartItem
                .builder()
                .size(request.size())
                .color(request.color())
                .cart(cart)
                .price(request.price())
                .skuCode(request.skuCode())
                .quantity(request.quantity())
                .build();
    }

    private CartResponse cartToResponse(Cart cart){
        Set<CartItemResponse> items = cart.getItems().stream().map(this::itemToResponse).collect(Collectors.toSet());
        return new CartResponse(items);
    }

    private CartItemResponse itemToResponse(CartItem item){
        return new CartItemResponse(item.getId(), item.getSkuCode(), item.getSize(), item.getColor(), item.getQuantity(), item.getPrice());
    }
}
