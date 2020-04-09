package com.gra.local.persistence.services;

import com.gra.local.persistence.repositories.VendorProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorProductService {

    @Autowired
    private VendorProductRepository vendorProductRepository;

    public VendorProductService() {
    }
}
