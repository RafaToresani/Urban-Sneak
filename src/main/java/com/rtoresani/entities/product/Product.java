package com.rtoresani.entities.product;

import com.rtoresani.entities.inventory.Inventory;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private String name;
    private String description;
    private String brand;
    private String material;
    private ECategory category;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true)
    private Set<ProductSize> sizes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true)
    private Set<ProductColor> colors;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true)
    private Set<Inventory> inventories;
    private Double price;
    private Boolean isActive;

    public void toggleStatus(){
        this.isActive = !this.isActive;
    }
}
