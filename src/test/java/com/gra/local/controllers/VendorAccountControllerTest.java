package com.gra.local.controllers;

import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class VendorAccountControllerTest extends BasicRequestTest {

    @Test
    public void testCreateVendorAccount_shouldReturn_StatusOK() {
        VendorAccountDto vendorAccountDto = new VendorAccountDto();
        vendorAccountDto.setName("Basil");
        vendorAccountDto.setCity("Sibiu");
        vendorAccountDto.setEmail("demo@email.com");
        vendorAccountDto.setPhone("+30009990999");

        ResponseEntity<VendorAccount> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/account/", vendorAccountDto, VendorAccount.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testValidatePhoneNumber_usingTwilio_API_shouldReturm_StatusOK() {
        String phoneNumber = System.getenv("TWILIO_DEV_TEST_PHONE_NR");
        ResponseEntity<Boolean> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/account/validate/" + phoneNumber, phoneNumber, Boolean.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertTrue(String.valueOf(postResponse), Boolean.TRUE);
    }

    @Test
    public void testVerifyAccount_usingTwilio_API_bySendingSMS_shouldReturn_StatusOK() {
        String to = System.getenv("TWILIO_RECEIVER_TEST_PHONE_NR");
        String message = "Demo message";
        ResponseEntity<Boolean> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/account/verifyAccount/" + to, message, Boolean.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertTrue(String.valueOf(postResponse), Boolean.TRUE);
    }
}
