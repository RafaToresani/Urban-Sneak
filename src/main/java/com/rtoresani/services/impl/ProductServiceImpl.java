package com.rtoresani.services.impl;

import com.rtoresani.dtos.requests.ProductRequest;
import com.rtoresani.dtos.responses.ProductResponse;
import com.rtoresani.entities.product.ECategory;
import com.rtoresani.entities.product.Product;
import com.rtoresani.entities.product.ProductColor;
import com.rtoresani.entities.product.ProductSize;
import com.rtoresani.exceptions.ResourceAlreadyExistsException;
import com.rtoresani.exceptions.ResourceNotFoundException;
import com.rtoresani.repositories.product.ProductColorRepository;
import com.rtoresani.repositories.product.ProductRepository;
import com.rtoresani.repositories.product.ProductSizeRepository;
import com.rtoresani.services.ProductService;
import com.rtoresani.specifications.ProductSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
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

    @Override
    public Page<ProductResponse> findAll(String name, String brand, String category, Double minPrice, Double maxPrice, Pageable pageable) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasBrand(brand))
                .and(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));

        Page<Product> products = productRepository.findAll(spec, pageable);

        return products.map(this::productToResponse);
    }

    @Override
    public ProductResponse findProductBySkuCode(String skuCode) {
        Optional<Product> opt = this.productRepository.findBySkuCode(skuCode);
        if(opt.isEmpty()) throw new ResourceNotFoundException("Error: Product with SKU CODE: '" + skuCode + "' doesn't exist.");
        return this.productToResponse(opt.get());
    }

    @Transactional
    public ProductResponse updateProduct(ProductRequest request) {
        Optional<Product> opt = this.productRepository.findBySkuCode(request.skuCode());
        if(opt.isEmpty()) throw new ResourceNotFoundException("Error: Product with SKU CODE: '" + request.skuCode() + "' doesn't exist.");

        this.checkCategory(request.category());
        Product existingProduct = opt.get();
        existingProduct.setName(request.name());
        existingProduct.setDescription(request.description());
        existingProduct.setCategory(ECategory.valueOf(request.category()));
        existingProduct.setBrand(request.brand());
        existingProduct.setPrice(request.price());
        existingProduct.setMaterial(request.material());

        return this.productToResponse(this.productRepository.save(existingProduct));
    }

    @Override
    public void toggleStatus(String skuCode) {
        Optional<Product> product = this.productRepository.findBySkuCode(skuCode);
        if(product.isEmpty()) throw new ResourceNotFoundException("Error: Product with SKU CODE: '" + skuCode + "' doesn't exist.");
        product.get().toggleStatus();
        this.productRepository.save(product.get());
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
                .inventories(new HashSet<>())
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
