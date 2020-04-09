package com.gra.local.controllers;

import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VendorAccountControllerTest extends BasicRequestTest {

    @Test
    public void testCreateVendorAccount_shouldReturn_StatusOK() {
        VendorAccountDto vendorAccountDto = new VendorAccountDto();
        vendorAccountDto.setName("Basil");
        vendorAccountDto.setCity("Sibiu");
        vendorAccountDto.setEmail("demo@email.com");
        vendorAccountDto.setPhone("+30009990999");

        ResponseEntity<VendorAccount> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/account", vendorAccountDto, VendorAccount.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
    }
}
