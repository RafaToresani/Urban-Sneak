package com.rtoresani.controllers;

import com.rtoresani.config.security.SecurityUtils;
import com.rtoresani.dtos.responses.CartResponse;
import com.rtoresani.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
