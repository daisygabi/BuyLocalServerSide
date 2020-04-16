package com.gra.local.controllers;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.services.VendorProductsService;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class VendorProductsController {

    private VendorProductsService vendorProductsService;

    @Autowired
    public VendorProductsController(VendorProductsService vendorProductsService) {
        this.vendorProductsService = vendorProductsService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(vendorProductsService.findAllProducts());
    }

    @PostMapping("/")
    public ResponseEntity<?> addProduct(@Valid @RequestBody VendorProductDto product) {
        VendorProduct addedProduct = vendorProductsService.save(product);
        return addedProduct != null ? ResponseEntity.ok(EntityHelper.convertToAbstractDto(addedProduct, VendorProductDto.class)) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody VendorProductDto product) {
        vendorProductsService.update(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteProduct(@Valid @RequestBody VendorProductDto product) {
        vendorProductsService.delete(product);
        return ResponseEntity.ok().build();
    }
}
