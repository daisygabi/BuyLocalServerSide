package com.gra.local.persistence.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductAndSelectedQuantity {
    private Long productId;
    private String productName;
    private String selectedQuantity;
}
