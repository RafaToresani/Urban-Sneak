package com.rtoresani.repositories.product;

import com.rtoresani.entities.product.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    void deleteByProductId(Long productId);
}
