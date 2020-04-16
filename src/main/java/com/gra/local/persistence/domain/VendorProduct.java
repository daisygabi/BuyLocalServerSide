package com.gra.local.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
public class VendorProduct extends DefaultDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "name")
    private String name;

    @Column(columnDefinition = "min_quantity_per_order")
    private Double minQuantityPerOrder;

    @Column(columnDefinition = "max_quantity_per_order")
    private Double maxQuantityPerOrder;

    @Column(columnDefinition = "quantity_type")
    private int quantityType;

    @Column(columnDefinition = "in_stock")
    private boolean inStock;

    @Column(columnDefinition = "price")
    private Double price;

    @Column(columnDefinition = "currency")
    private String currency;

    @Column(columnDefinition = "vendor_id")
    private Long vendorId;
}

