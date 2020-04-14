package com.gra.local.controllers;

import com.gra.local.BasicRequestTest;
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
        vendorAccountDto.setVerified(false);

        ResponseEntity<VendorAccount> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/account/", vendorAccountDto, VendorAccount.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
    }

    // Failing with Resource not accessible with Test Account Credentials
    @Test(expected = com.twilio.exception.ApiException.class)
    public void testValidatePhoneNumber_usingTwilio_API_shouldReturm_StatusOK() {
        String phoneNumber = System.getenv("TWILIO_DEV_TEST_PHONE_NR");
        ResponseEntity<Boolean> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/account/validate/" + phoneNumber, phoneNumber, Boolean.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertTrue(String.valueOf(postResponse), Boolean.TRUE);
    }

    // Failing with Resource not accessible with Test Account Credentials
    @Test(expected = com.twilio.exception.ApiException.class)
    public void testVerifyAccount_usingTwilio_API_bySendingSMS_shouldReturn_StatusOK() {
        String number = System.getenv("TWILIO_DEV_TEST_PHONE_NR");

        VendorAccountDto vendorAccountDto = new VendorAccountDto();
        vendorAccountDto.setName("Basil");
        vendorAccountDto.setCity("Sibiu");
        vendorAccountDto.setEmail("TEST@email.com");
        vendorAccountDto.setPhone(number);
        vendorAccountDto.setVerified(false);

        getRestTemplate().postForEntity(getRootUrl() + "/account/", vendorAccountDto, VendorAccount.class);
        ResponseEntity<Boolean> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/account/verifyAccount/" + number, number, Boolean.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertTrue(String.valueOf(postResponse), Boolean.TRUE);
    }
}
