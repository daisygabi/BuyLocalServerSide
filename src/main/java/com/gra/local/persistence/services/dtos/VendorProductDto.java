package com.gra.local.persistence.services.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class VendorProductDto {

    private Long id;
    private String name;
    private Double minQuantityPerOrder;
    private Double maxQuantityPerOrder;
    private int quantityTypeId;
    private boolean inStock;
    private Double price;
    private String currency;
    private Long vendorId;
    private String vendorName;
}
