package com.gra.local.persistence.services;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.repositories.VendorProductRepository;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorProductService {

    private VendorProductRepository vendorProductRepository;

    @Autowired
    public void setUserRepository(VendorProductRepository vendorProductRepository) {
        this.vendorProductRepository = vendorProductRepository;
    }

    public VendorProduct save(VendorProductDto dto) {
        return getVendorProductRepository().save(EntityHelper.convertToAbstractEntity(dto, VendorProduct.class));
    }

    public VendorProductRepository getVendorProductRepository() {
        return vendorProductRepository;
    }
}
