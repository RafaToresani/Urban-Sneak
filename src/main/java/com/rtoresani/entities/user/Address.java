package com.rtoresani.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String street;
    private String apartment;
    private String deliveryInstructions;
    @ManyToOne
    @JoinColumn(name = "user_info")
    @JsonBackReference
    private UserInfo userInfo;
}
