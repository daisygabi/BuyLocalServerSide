package com.gra.local.persistence.services.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorAccountLoginResponse {
    private String token;
    private Long accountId;
    private String phone;
    private String email;

    public VendorAccountLoginResponse(String token, Long accountId, String phone, String email) {
        this.token = token;
        this.accountId = accountId;
        this.phone = phone;
        this.email = email;
    }
}
