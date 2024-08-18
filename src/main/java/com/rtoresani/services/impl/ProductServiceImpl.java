package com.rtoresani.services.impl;

import com.rtoresani.dtos.requests.ProductRequest;
import com.rtoresani.dtos.responses.ProductResponse;
import com.rtoresani.entities.product.ECategory;
import com.rtoresani.entities.product.Product;
import com.rtoresani.entities.product.ProductColor;
import com.rtoresani.entities.product.ProductSize;
import com.rtoresani.exceptions.ResourceAlreadyExistsException;
import com.rtoresani.repositories.product.ProductColorRepository;
import com.rtoresani.repositories.product.ProductRepository;
import com.rtoresani.repositories.product.ProductSizeRepository;
import com.rtoresani.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ProductColorRepository productColorRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if(this.productRepository.existsBySkuCode(request.skuCode())) throw new ResourceAlreadyExistsException("ERROR: Product with SKUCODE: '" + request.skuCode() + "' already exists");
        this.checkCategory(request.category());
        Product product = this.requestToProduct(request);

        this.addColors(product, request.colors());
        this.addSizes(product, request.sizes());

        return this.productToResponse(this.productRepository.save(product));
    }



    private void checkCategory(String category){
        try {
            ECategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ERROR: Category: '" + category + "' is not allowed.");
        }
    }

    private Product requestToProduct(ProductRequest request){
        return Product
                .builder()
                .skuCode(request.skuCode())
                .name(request.name())
                .brand(request.brand())
                .description(request.description())
                .category(ECategory.valueOf(request.category()))
                .price(request.price())
                .colors(new HashSet<>())
                .sizes(new HashSet<>())
                .material(request.material())
                .isActive(true)
                .build();
    }

    private void addColors(Product product, Set<String> colors){
        colors.forEach(
                color -> product.getColors().add(ProductColor
                        .builder()
                        .color(color)
                        .product(product)
                        .build())
        );
    }

    private void addSizes(Product product, Set<String> sizes){
        sizes.forEach(
                size -> product.getSizes().add(ProductSize
                        .builder()
                        .size(size)
                        .product(product)
                        .build())
        );
    }

    private ProductResponse productToResponse(Product product) {
        return new ProductResponse(
                product.getSkuCode(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getBrand(),
                product.getPrice(),
                product.getSizes().stream()
                        .map(ProductSize::getSize)
                        .collect(Collectors.toSet()),
                product.getColors().stream()
                        .map(ProductColor::getColor)
                        .collect(Collectors.toSet()),
                product.getMaterial(),
                product.getIsActive()
        );
    }
}
