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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
        return cartToResponse(this.findCartByEmail(email));
    }

    @Override
    public CartResponse addToCart(CartItemRequest itemRequest, String email) throws BadRequestException {
        Cart cart = this.findCartByEmail(email);
        this.inventoryService.checkAndReserveProduct(itemRequest.skuCode(), itemRequest.size(), itemRequest.color(), itemRequest.quantity());
        CartItem cartItem = this.requestToCartItem(cart, itemRequest);
        cart.getItems().add(cartItem);
        cart.setLastUpdate(LocalDateTime.now());
        return this.cartToResponse(this.cartRepository.save(cart));
    }

    @Transactional
    public void cleanCart(String email) {
        Cart cart = this.findCartByEmail(email);
        Iterator<CartItem> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            this.deleteItemFromCart(cart, item.getId());
            iterator.remove();
        }
        this.cartRepository.save(cart);
    }

    @Override
    public void deleteItemFromCart(Long itemId, String email) {
        Cart cart = this.findCartByEmail(email);
        this.deleteItemFromCart(cart, itemId);
        cart.getItems().removeIf(item -> Objects.equals(item.getId(), itemId));
        this.cartRepository.save(cart);
    }

    @Override
    public void updateQuantity(Long itemId, Integer quantity) {
        Optional<CartItem> optItem = this.itemRepository.findById(itemId);
        if(optItem.isEmpty()) throw new ResourceNotFoundException("Item doesn't exists");
        if(quantity<1) throw new IllegalArgumentException("Error: Quantity cannot be less than 1");
        if(optItem.get().getQuantity()<quantity){
            this.increaseQuantity(optItem.get(), quantity-optItem.get().getQuantity());
        } else if (optItem.get().getQuantity()>quantity) {
            this.decreaseQuantity(optItem.get(), optItem.get().getQuantity()-quantity);
        }
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    private void decreaseQuantity(CartItem item, int quantity) {
        this.inventoryService.increaseInventoryQuantity(item.getSkuCode(), item.getColor(), item.getSize(), quantity);
        item.setQuantity(item.getQuantity()-quantity);
        this.itemRepository.save(item);
    }
    private void increaseQuantity(CartItem item, int quantity) {
        this.inventoryService.decreaseInventoryQuantity(item.getSkuCode(), item.getSize(), item.getColor(), quantity);
        item.setQuantity(item.getQuantity()+quantity);
        this.itemRepository.save(item);
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

    @Transactional
    private void deleteItemFromCart(Cart cart, Long itemId){
        Optional<CartItem> optItem = this.itemRepository.findById(itemId);
        if(optItem.isEmpty()) throw new ResourceNotFoundException("Item doesn't exists");
        CartItem item = optItem.get();
        this.inventoryService.increaseInventoryQuantity(item.getSkuCode(), item.getColor(), item.getSize(), item.getQuantity());
        this.itemRepository.delete(item);
    }

    private CartResponse cartToResponse(Cart cart){
        Set<CartItemResponse> items = cart.getItems().stream().map(this::itemToResponse).collect(Collectors.toSet());
        return new CartResponse(items);
    }

    private CartItemResponse itemToResponse(CartItem item){
        return new CartItemResponse(item.getId(), item.getSkuCode(), item.getSize(), item.getColor(), item.getQuantity(), item.getPrice());
    }

    @Scheduled(cron="0 0 */24 * * *")
    private void cleanInactiveCarts(){
        LocalDateTime now = LocalDateTime.now();
        List<Cart> carts = cartRepository.findAll();
        carts.forEach(cart -> {
            if (ChronoUnit.HOURS.between(cart.getLastUpdate(), now)>= 72){
                this.cleanCart(cart.getUser().getEmail());
            }
        });
    }


    private Cart findCartByEmail(String email){
        Optional<Cart> cart = this.cartRepository.findByUserEmail(email);
        if(cart.isEmpty()) throw new ResourceNotFoundException("User doesn't have a cart");
        return cart.get();
    }
}
