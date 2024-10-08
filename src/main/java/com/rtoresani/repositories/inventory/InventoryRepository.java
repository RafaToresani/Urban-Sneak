package com.rtoresani.repositories.inventory;

import com.rtoresani.dtos.responses.InventoryResponse;
import com.rtoresani.entities.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductSkuCodeAndColorAndSize(String skuCode, String color, String size);

    Boolean existsByProductSkuCodeAndColorAndSize(String skuCode, String color, String size);

    List<Inventory> findAllByProductSkuCode(String skuCode);
}
