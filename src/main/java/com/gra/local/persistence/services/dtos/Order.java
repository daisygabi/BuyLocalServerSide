package com.gra.local.persistence.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    private Long vendorId;
    private String vendorName;
    private ProductAndSelectedQuantity[] products;
}
