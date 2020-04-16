package com.gra.local.persistence.services;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.domain.VendorProduct;
import com.gra.local.persistence.repositories.VendorProductsRepository;
import com.gra.local.persistence.services.dtos.VendorProductDto;
import com.gra.local.persistence.services.dtos.VendorsAndTheirProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<VendorProduct> findAllProducts() {
        return getVendorProductsRepository().findAll();
    }

    public List<VendorProduct> findAllProductsByVendorId(Long vendorId) {
        return getVendorProductsRepository().findAllProductsByVendorId(vendorId);
    }

    public VendorsAndTheirProductsResponse associateVendorWithProducts(VendorAccount account) {
        VendorsAndTheirProductsResponse vendorsAndTheirProductsResponse = new VendorsAndTheirProductsResponse();
        vendorsAndTheirProductsResponse.setVendorId(account.getId());
        vendorsAndTheirProductsResponse.setVendorName(account.getName());
        vendorsAndTheirProductsResponse.setProducts(findAllProductsByVendorId(account.getId()));

        return vendorsAndTheirProductsResponse;
    }

    public List<VendorsAndTheirProductsResponse> aggregateVendorsWithTheirProducts(final List<VendorAccount> verifiedVendors) {
        List<VendorsAndTheirProductsResponse> aggregatedData = new ArrayList<>();
        for (VendorAccount account : verifiedVendors) {
            aggregatedData.add(associateVendorWithProducts(account));
        }
        return aggregatedData;
    }
}
