package com.gra.local.persistence.services.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorAccountDto {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String city;
}
