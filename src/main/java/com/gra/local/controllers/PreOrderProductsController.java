package com.gra.local.controllers;

import com.gra.local.exceptions.CustomException;
import com.gra.local.persistence.services.PreOrderProductsService;
import com.gra.local.persistence.services.dtos.VendorsAndTheirProductsResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pre-order")
public class PreOrderProductsController {

    private PreOrderProductsService preOrderProductsService;

    @Autowired
    public PreOrderProductsController(PreOrderProductsService preOrderProductsService) {
        this.preOrderProductsService = preOrderProductsService;
    }

    @PostMapping("/")
    public ResponseEntity<?> preOrderProducts(@Valid @NonNull @RequestBody List<VendorsAndTheirProductsResponse> vendorsAndTheirProducts) {
        try {
            // For all the vendors that have products in the list, send them pre-order SMS. They should confirm by SMS as well
            for (VendorsAndTheirProductsResponse data : vendorsAndTheirProducts) {
                preOrderProductsService.sendOrderDetailsBySmsToVendors(data.getCustomerPhoneNumber(), data.getProducts());
            }
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            throw new CustomException("Error sending vendors sms with order details", HttpStatus.BAD_REQUEST, ex);
        }
    }
}
