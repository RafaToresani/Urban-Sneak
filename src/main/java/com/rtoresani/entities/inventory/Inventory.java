package com.rtoresani.entities.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rtoresani.entities.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String size;
    private String color;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonBackReference
    private Product product;
}
