package com.gra.local.controllers;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.services.VendorAccountService;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class VendorAccountController {

    private VendorAccountService vendorAccountService;

    @Autowired
    public VendorAccountController(VendorAccountService vendorAccountService) {
        this.vendorAccountService = vendorAccountService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createAccount(@Valid @RequestBody VendorAccountDto accountDto) {
        VendorAccount createdVendorAccount = vendorAccountService.save(accountDto);
        return createdVendorAccount != null ? ResponseEntity.ok(EntityHelper.convertToAbstractDto(createdVendorAccount, VendorAccountDto.class)) : ResponseEntity.badRequest().build();
    }
}
