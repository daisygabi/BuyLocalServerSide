package com.gra.local.controllers;

import com.gra.local.exceptions.CustomException;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.services.VendorAccountService;
import com.gra.local.persistence.services.dtos.VendorAccountLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AccountLoginController {

    private VendorAccountService vendorAccountService;

    @Autowired
    public AccountLoginController(VendorAccountService vendorAccountService) {
        this.vendorAccountService = vendorAccountService;
    }

    @PostMapping("/login")
    public ResponseEntity<VendorAccountLoginResponse> login(@Valid @RequestBody VendorAccount user) {
        try {
            VendorAccount foundVendorAccount = vendorAccountService.findByPhone(user.getPhone());
            if (foundVendorAccount != null) {
                String loginToken = vendorAccountService.login(user.getPhone(), user.getPassword());
                VendorAccountLoginResponse response = new VendorAccountLoginResponse(loginToken, foundVendorAccount.getId(), foundVendorAccount.getPhone(), foundVendorAccount.getEmail());
                return ResponseEntity.ok(response);
            }
        } catch (Exception exception) {
            throw new CustomException("No account with provided credentials is found.", HttpStatus.NOT_FOUND, exception);
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
