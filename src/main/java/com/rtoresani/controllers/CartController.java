package com.rtoresani.controllers;

import com.rtoresani.config.security.SecurityUtils;
import com.rtoresani.dtos.requests.CartItemRequest;
import com.rtoresani.dtos.responses.CartResponse;
import com.rtoresani.services.CartService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //      G E T
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER', 'MANAGER')")
    public CartResponse getCart(){
        String email = SecurityUtils.getCurrentUserEmail();
        return this.cartService.getCart(email);
    }

    //      P O S T
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER', 'MANAGER')")
    public CartResponse addToCart(@RequestBody CartItemRequest itemRequest) throws BadRequestException {
        String email = SecurityUtils.getCurrentUserEmail();
        return this.cartService.addToCart(itemRequest, email);
    }

    //      D E L E T E
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER', 'MANAGER')")
    public void cleanCart(){
        String email = SecurityUtils.getCurrentUserEmail();
        this.cartService.cleanCart(email);
    }

    @DeleteMapping("/delete-item")
    @ResponseStatus(HttpStatus.OK)
    public void deleteItemFromCart(@RequestParam(name = "item_id") Long itemId){
        String email = SecurityUtils.getCurrentUserEmail();
        this.cartService.deleteItemFromCart(itemId, email);
    }
}
