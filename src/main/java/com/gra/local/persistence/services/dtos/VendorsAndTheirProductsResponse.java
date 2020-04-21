package com.gra.local.persistence.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendorsAndTheirProductsResponse {
    private Long vendorId;
    private String vendorName;
    private List<VendorProductDto> products;
    private String customerPhoneNumber;
}
