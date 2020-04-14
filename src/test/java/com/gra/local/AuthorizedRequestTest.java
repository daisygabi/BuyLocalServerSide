package com.gra.local;

import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.services.dtos.VendorAccountLoginResponse;
import org.junit.Before;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public abstract class AuthorizedRequestTest extends BasicRequestTest {
    private VendorAccount vendorAccount;
    private String loginToken;

    public VendorAccount getVendorAccount() {
        return vendorAccount;
    }

    public String getLoginToken() {
        return loginToken;
    }

    @Before
    public void loginExistingUser() {
        vendorAccount = new VendorAccount();
        vendorAccount.setEmail("something@gmail.com");
        vendorAccount.setPhone("+21324354");
        vendorAccount.setPassword("admin123456789");

        VendorAccountLoginResponse response = login(vendorAccount);
        loginToken = response.getToken();
        vendorAccount.setId(response.getAccountId());
    }

    protected VendorAccountLoginResponse login(VendorAccount existingUser) {
        ResponseEntity<VendorAccountLoginResponse> loginResponse = getRestTemplate().postForEntity(getRootUrl() + "/login", existingUser, VendorAccountLoginResponse.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        return loginResponse.getBody();
    }

    protected ResponseEntity<VendorAccount> findVendorAccount(Long userId) {
        return getRestTemplate().exchange(getRootUrl() + "/account/" + userId, HttpMethod.GET, buildEmptyHttpEntity(), VendorAccount.class);
    }

    protected <TBody> HttpEntity<?> buildHTTPEntity(TBody body) {
        return buildHTTPEntity(body, loginToken);
    }

    protected HttpEntity<?> buildEmptyHttpEntity() {
        return buildEmptyHttpEntity(loginToken);
    }
}
