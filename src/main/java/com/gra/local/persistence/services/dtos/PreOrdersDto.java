package com.gra.local.persistence.services.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreOrdersDto {

    private Long id;
    private Long vendorId;
    private String customerPhoneNumber;
    private String[] productIds;
    private boolean acceptedByVendor;
    private String acceptLink;
    private String denyLink;
}
