package com.rtoresani.repositories.product;

import com.rtoresani.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsBySkuCode(String skuCode);
    Optional<Product> findBySkuCode(String skuCode);
    @Query("SELECT COUNT(p) > 0 FROM Product p JOIN p.colors c JOIN p.sizes s " +
            "WHERE p.skuCode = :skuCode AND c.color = :color AND s.size = :size")
    boolean existsBySkuCodeAndColorAndSize(
            @Param("skuCode") String skuCode,
            @Param("color") String color,
            @Param("size") String size);
}
