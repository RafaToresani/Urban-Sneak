package com.rtoresani.services;

import com.rtoresani.dtos.requests.ProductRequest;
import com.rtoresani.dtos.responses.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
}
