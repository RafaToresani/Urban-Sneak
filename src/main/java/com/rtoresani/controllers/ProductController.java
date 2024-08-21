package com.rtoresani.controllers;

import com.rtoresani.dtos.requests.ProductRequest;
import com.rtoresani.dtos.responses.ProductResponse;
import com.rtoresani.services.ProductService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    //      P O S T
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ProductResponse createProduct(@RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());

        return this.productService.createProduct(productRequest);
    }

    //      G E T
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> findAllProducts(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) String category,
                                                 @RequestParam(required = false, name = "min") Double min_price,
                                                 @RequestParam(required = false, name = "max") Double max_price,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "DESC") String sort,
                                                 @RequestParam(defaultValue = "name", name = "sort-value") String sortValue){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), sortValue));
        return this.productService.findAll(name, brand, category, min_price, max_price, pageable);
    }

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse findProduct(@PathVariable(name = "sku-code") String skuCode){
        return this.productService.findProductBySkuCode(skuCode);
    }

    //      P U T
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ProductResponse updateProduct(@RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());

        return this.productService.updateProduct(productRequest);
    }

    //      D E L E T E
    @DeleteMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public void toggleProduct(@PathVariable(name = "sku-code") String skuCode){
        this.productService.toggleStatus(skuCode);
    }
}
