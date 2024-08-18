package com.rtoresani.services;

import com.rtoresani.dtos.requests.ProductRequest;
import com.rtoresani.dtos.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    Page<ProductResponse> findAll(String name, String brand, String category, Double minPrice, Double maxPrice, Pageable pageable);

    ProductResponse findProductBySkuCode(String skuCode);

    ProductResponse updateProduct(ProductRequest productRequest);
}
