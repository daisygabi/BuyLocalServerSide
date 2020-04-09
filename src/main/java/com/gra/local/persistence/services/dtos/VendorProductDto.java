package com.gra.local.persistence.services.dtos;

import com.gra.local.persistence.domain.QuantityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorProductDto {

    private Long id;
    private String name;
    private Double minQuantityPerOrder;
    private Double maxQuantityPerOrder;
    private int quantityTypeId;
}
