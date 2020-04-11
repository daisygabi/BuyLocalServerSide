package com.gra.local.controllers;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.services.VendorAccountService;
import com.gra.local.persistence.services.dtos.VendorAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/validate/{lookupNumber}")
    public ResponseEntity<?> validatePhoneNumber(@Valid @PathVariable("lookupNumber") String lookupNumber) {
        boolean validNumber = vendorAccountService.validatePhoneNumber(lookupNumber);
        return validNumber ? ResponseEntity.ok(validNumber) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/verifyAccount/{toNumber}")
    public ResponseEntity<Boolean> verifyAccount(@Valid @PathVariable("toNumber") String toNumber) {
        if (!vendorAccountService.checkIfVendorPhoneNumberIsVerified(toNumber)) {
            vendorAccountService.verifyAccount(toNumber);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/confirmCode/{code}")
    public ResponseEntity<Boolean> confirmCode(@Valid @PathVariable("code") String code) {
        boolean verifyCode = vendorAccountService.verifyCode(code);
        if (verifyCode) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
