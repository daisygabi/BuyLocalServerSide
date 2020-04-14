package com.gra.local.controllers;

import com.gra.local.BasicRequestTest;
import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.services.dtos.Role;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import com.gra.local.persistence.services.dtos.VendorAccountLoginResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;

import static org.junit.Assert.*;

public class AccountLoginControllerTest extends BasicRequestTest {

    private  VendorAccount vendorAccount;
    private final String phone = "+21321324324";
    private final String password = "3542rDSFDSfgadfs";

    @Before
    public void setUp() throws Exception {
        vendorAccount = new VendorAccount();
        vendorAccount.setPhone(phone);
        vendorAccount.setPassword(password);
        vendorAccount.setId(1L);
        vendorAccount.setVerified(false);
        vendorAccount.setEmail("test@email.com");
        vendorAccount.setName("Gigel");
        vendorAccount.setCity("Bohinj");
        vendorAccount.setRole(Role.VENDOR);

        // Create account
        ResponseEntity<VendorAccount> createAccountResponse = getRestTemplate().postForEntity(getRootUrl() + "/account/", EntityHelper.convertToAbstractDto(vendorAccount, VendorAccountDto.class), VendorAccount.class);
        assertNotNull(createAccountResponse.getBody());

        VendorAccount createdAccount = createAccountResponse.getBody();
        createdAccount.setPassword(password);
        createdAccount.setVerified(true);

        // Add password to account
        getRestTemplate().put(getRootUrl() + "/account/password", createdAccount);
    }

    @Test
    public void testLogin() {
        ResponseEntity<VendorAccountLoginResponse> postResponse = getRestTemplate().postForEntity(getRootUrl() + "/login", vendorAccount, VendorAccountLoginResponse.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testOptionsRequests() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Origin", "https://foo.example");
        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> optionsResponse = getRestTemplate().exchange(getRootUrl() + "/login", HttpMethod.OPTIONS, objectHttpEntity, String.class);
        assertEquals(HttpStatus.OK, optionsResponse.getStatusCode());
        assertTrue(optionsResponse.getHeaders().containsKey("Access-Control-Allow-Origin"));
    }
}
