package com.rtoresani.repositories.product;

import com.rtoresani.entities.product.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
    void deleteByProductId(Long productId);
}
