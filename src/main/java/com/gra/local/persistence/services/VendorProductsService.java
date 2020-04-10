package com.gra.local.persistence.services;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.repositories.VendorProductsRepository;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class VendorProductsService {

    private VendorProductsRepository vendorProductsRepository;

    @Autowired
    public void setVendorProductsRepository(VendorProductsRepository vendorProductsRepository) {
        this.vendorProductsRepository = vendorProductsRepository;
    }

    public VendorProduct save(VendorProductDto dto) {
        return getVendorProductsRepository().save(EntityHelper.convertToAbstractEntity(dto, VendorProduct.class));
    }

    public void update(VendorProductDto dto) {
        Optional<VendorProduct> optionalProduct = getVendorProductsRepository().findById(dto.getId());
        optionalProduct.ifPresent(product -> {
            dto.setId(product.getId());
            getVendorProductsRepository().update(dto);
        });
    }

    public void delete(Long productId) {
        getVendorProductsRepository().deleteById(productId);
    }

    public void delete(VendorProductDto vendorProductDto) {
        getVendorProductsRepository().delete(EntityHelper.convertToAbstractEntity(vendorProductDto, VendorProduct.class));
    }

    public VendorProductsRepository getVendorProductsRepository() {
        return vendorProductsRepository;
    }
}
